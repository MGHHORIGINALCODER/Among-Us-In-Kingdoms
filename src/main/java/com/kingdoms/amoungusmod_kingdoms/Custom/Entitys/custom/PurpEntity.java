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
import net.minecraft.entity.passive.TameableEntity;
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
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PurpEntity extends TameableEntity {

    // Animation Controllers
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sitDownState = new AnimationState();
    public final AnimationState sitIdleState = new AnimationState();
    public final AnimationState sitUpState = new AnimationState();

    // Isolated system tracking clock registers
    private int idleAnimationTimeout = 0;
    private int pticks;
    private int tick = 0;
    private int seconds = 0;
    private boolean IsSitting=false;
    private boolean TimerOn=false;
    private int TimerLength=0;
    private Runnable runnableEx;

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }



    public enum SitState { STANDING, SITTING_DOWN, SITTING_IDLE, SITTING_UP }



    private static final int SIT_DOWN_DURATION = 15;
    private static final int SIT_UP_DURATION = 15;

    public PurpEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }







    public static DefaultAttributeContainer.Builder createTestEntityA(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1000)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,2f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK,2f);

    }



    private void setUpAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }




    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClient){
            setUpAnimationStates();
        }
        if(TimerOn) {

            tick++;
            if (tick >= 20) {
                tick = 0;
                seconds++;
                if(seconds>=TimerLength){
                    seconds=0;
                    TimerOn=false;
                    TimerLength=0;
                    runnableEx.run();

                }

            }
        }

    }

    // Freezes motor controls so the mob stays in place while sitting down
    // 1. Only stop navigation if the entity is SITTING DOWN or actively SITTING IDLE
    @Override
    public void mobTick() {
        super.mobTick();

    }






    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if(!this.isTamed()){
            if(isBreedingItem(itemStack)){
                this.setOwner(player);
                this.setTamed(true);
                return ActionResult.SUCCESS;

            }
            return ActionResult.PASS;
        }

        if(this.isOwner(player) && !IsSitting){
            this.getNavigation().stop();
            this.idleAnimationState.stop();
            this.sitDownState.start(age);
            this.setSitting(true);
            TimerLength=SIT_DOWN_DURATION;
            TimerOn=true;
            runnableEx=()->{
                this.sitDownState.stop();
                this.sitIdleState.start(age);
            };
            IsSitting=true;
            return ActionResult.SUCCESS;


        }
        if(this.isOwner(player) && IsSitting){

            this.sitIdleState.stop();
            this.sitUpState.start(age);
            TimerLength=SIT_UP_DURATION;
            TimerOn=true;
            runnableEx=()->{
                this.sitDownState.stop();
                this.idleAnimationState.start(age);
                this.setSitting(false);
            };
            IsSitting=false;
            return ActionResult.SUCCESS;


        }
        return ActionResult.PASS;

    }




    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FollowParentGoal(this, 1.4D));
        this.goalSelector.add(2,new TrackOwnerAttackerGoal(this));
        this.goalSelector.add(3,new FollowOwnerGoal(this,1.2D,1,30,false));
        this.goalSelector.add(4, new AttackWithOwnerGoal(this));
        this.goalSelector.add(4, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.AMETHYST_SHARD), false));
        this.goalSelector.add(5, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(6, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
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