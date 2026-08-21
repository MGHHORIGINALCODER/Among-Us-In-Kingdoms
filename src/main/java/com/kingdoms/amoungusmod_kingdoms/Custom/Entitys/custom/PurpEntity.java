package com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.custom;

import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.ModEntities;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.ToolsAndBits;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PurpEntity extends AnimalEntity {

    // Animation Controllers
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sitDownState = new AnimationState();
    public final AnimationState sitIdleState = new AnimationState();
    public final AnimationState sitUpState = new AnimationState();

    // Isolated system tracking clock registers
    private int idleAnimationTimeout = 0;
    private int phaseTicks = 0;
    private int tick = 0;
    private int seconds = 0;

    public enum SitState { STANDING, SITTING_DOWN, SITTING_IDLE, SITTING_UP }

    // Data Trackers for Server -> Client Multi-player Packet Sync
    private static final TrackedData<Integer> SIT_STATE_ID = DataTracker.registerData(
            PurpEntity.class, TrackedDataHandlerRegistry.INTEGER
    );

    private static final int SIT_DOWN_DURATION = 15; // 0.75 Seconds
    private static final int SIT_UP_DURATION = 15;

    public PurpEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SIT_STATE_ID, SitState.STANDING.ordinal());
    }

    public SitState getCurrentState() {
        return SitState.values()[this.dataTracker.get(SIT_STATE_ID)];
    }

    public void setCurrentState(SitState state) {
        this.dataTracker.set(SIT_STATE_ID, state.ordinal());
    }

    // Client Listener intercepts network shifts to swap animations smoothly
    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (this.getWorld().isClient() && SIT_STATE_ID.equals(data)) {
            SitState newState = getCurrentState();
            switch (newState) {
                case SITTING_DOWN -> {
                    this.sitUpState.stop();
                    this.sitIdleState.stop();
                    this.sitDownState.start(this.age);
                }
                case SITTING_IDLE -> {
                    this.sitDownState.stop();
                    this.sitUpState.stop();
                    this.sitIdleState.start(this.age);
                }
                case SITTING_UP -> {
                    this.sitDownState.stop();
                    this.sitIdleState.stop();
                    this.sitUpState.start(this.age);
                }
                case STANDING -> {
                    this.sitUpState.stop();
                    this.sitDownState.stop();
                    this.sitIdleState.stop();
                }
            }
        }
        super.onTrackedDataSet(data);
    }

    public static DefaultAttributeContainer.Builder createTestEntityA(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1000)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    private void handleAnimationSequence() {
        SitState state = getCurrentState();
        if (state == SitState.STANDING) return;

        phaseTicks++;

        switch (state) {
            case SITTING_DOWN:
                if (phaseTicks >= SIT_DOWN_DURATION) {
                    this.setCurrentState(SitState.SITTING_IDLE);
                    this.phaseTicks = 0;
                }
                break;

            case SITTING_UP:
                if (phaseTicks >= SIT_UP_DURATION) {
                    this.setCurrentState(SitState.STANDING);
                    this.phaseTicks = 0;
                }
                break;

            default:
                break;
        }
    }

    private void setUpAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    // Unified helper method checks for physical drift or neurological path intentions
    public boolean IsMoving(){
        boolean physicalVelocity = this.getVelocity().horizontalLengthSquared() > 0.002D;
        boolean brainNavigation = this.getTarget() != null || !this.getNavigation().isIdle();
        return physicalVelocity || brainNavigation;
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        // 1. Client-Side Rendering Framework Loop
        if(this.getWorld().isClient()){
            setUpAnimationStates();
            handleAnimationSequence();
        }

        // 2. Server-Side Logical Simulation Loops
        if (!this.getWorld().isClient()) {
            SitState state = getCurrentState();

            if (!this.getWorld().isClient()) {


                if (state == SitState.STANDING) {
                    tick++;
                    if (tick >= 20) {
                        tick = 0;
                        seconds++;
                        if (seconds >= 10 && !this.IsMoving()) {
                            this.setCurrentState(SitState.SITTING_DOWN);
                            this.phaseTicks = 0;
                            this.seconds = 0;
                        }
                    }
                } else if (state == SitState.SITTING_IDLE) {
                    // Only trigger standing up via movement if it's already sitting idle
                    if (this.IsMoving()) {
                        this.setCurrentState(SitState.SITTING_UP);
                        this.phaseTicks = 0;
                        this.seconds = 0;
                    }
                } else {
                    // If the state is SITTING_DOWN or SITTING_UP, freeze all timers
                    tick = 0;
                    seconds = 0;
                }
            }


            // Detect movement and transition back up
            if (this.IsMoving() && state == SitState.SITTING_IDLE) {
                this.setCurrentState(SitState.SITTING_UP);
                this.phaseTicks = 0;
                this.seconds = 0;
            }
        }
    }

    // Freezes motor controls so the mob stays in place while sitting down
    // 1. Only stop navigation if the entity is SITTING DOWN or actively SITTING IDLE
    @Override
    public void mobTick() {
        super.mobTick();
        SitState state = getCurrentState();

        // CHANGED: Removed SITTING_UP from blocking navigation
        if (state == SitState.SITTING_DOWN || state == SitState.SITTING_IDLE) {
            this.getNavigation().stop();
        }
    }

    // 2. Only freeze physics and velocity if it's sitting down or sitting idle
    @Override
    public void travel(net.minecraft.util.math.Vec3d movementInput) {
        SitState state = getCurrentState();

        // CHANGED: Allows natural movement inputs to calculate when state shifts to SITTING_UP
        if (state == SitState.SITTING_DOWN || state == SitState.SITTING_IDLE) {
            super.travel(net.minecraft.util.math.Vec3d.ZERO);
        } else {
            super.travel(movementInput);
        }
    }


    // Clicking toggles sitting manually over networks
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        // Only run logic on the server side to handle registry changes safely
        if (hand == Hand.MAIN_HAND && !this.getWorld().isClient()) {
            SitState state = this.getCurrentState();

            if (state == SitState.STANDING) {
                // Player clicks while standing -> Go down
                this.setCurrentState(SitState.SITTING_DOWN);
                this.phaseTicks = 0; // Reset frame timer for sit down
                return ActionResult.SUCCESS;
            }
            else if (state == SitState.SITTING_IDLE) {
                // Player clicks while sitting down -> FORCE UNSIT (Stand Up)
                this.setCurrentState(SitState.SITTING_UP);
                this.phaseTicks = 0; // Reset frame timer for sit up animation sequence
                return ActionResult.SUCCESS;
            }
        }
        return super.interactMob(player, hand);
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FollowParentGoal(this, 1.4D));
        this.goalSelector.add(2, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.AMETHYST_SHARD), false));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(4, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING && getCurrentState() == SitState.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.AMETHYST_SHARD);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CAT_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ALLAY_DEATH;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    @Override
    protected void onKilledBy(@Nullable LivingEntity adversary) {
        this.dropItem(Items.AMETHYST_SHARD);
        if (adversary != null && adversary.getServer() != null) {ToolsAndBits.broadcastTypewriterActionbar(adversary.getServer(), "How dare you...", Formatting.RED);
            if (adversary instanceof PlayerEntity player)
            {player.playSound(ModSounds.MURDER_SOUND, 1, 1);player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 600));}
        }
        super.onKilledBy(adversary);}
    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {return ModEntities.PURP_ENTITY.create(world);}}