package com.github.revival.server.entity.mob;

import com.github.revival.server.entity.mob.test.EntityNewPrehistoric;
import com.github.revival.server.enums.EnumPrehistoric;
import com.github.revival.server.enums.EnumPrehistoricAI.*;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySmilodon extends EntityNewPrehistoric {

    public static final double baseDamage = 1;
    public static final double maxDamage = 5;
    public static final double baseHealth = 8;
    public static final double maxHealth = 34;
    public static final double baseSpeed = 0.25D;
    public static final double maxSpeed = 0.3D;

    public EntitySmilodon(World world) {
        super(world, EnumPrehistoric.Smilodon);
        this.setSize(0.9F, 0.8F);
    	this.pediaScale = 17F;
    	this.nearByMobsAllowed = 7;
        minSize = 0.5F;
        maxSize = 1.7F;
        teenAge = 4;
        developsResistance = true;
        breaksBlocks = false;
        favoriteFood = Items.beef;
        hasBabyTexture = false;
    }

	public int getAttackLength() {
		return 30;
	}

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(baseSpeed);
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(baseHealth);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(baseDamage);
    }

    @Override
    public void setSpawnValues() {
    }

    @Override
    public Activity aiActivityType() {

        return Activity.DIURINAL;
    }

    @Override
    public Attacking aiAttackType() {

        return Attacking.JUMP;
    }

    @Override
    public Climbing aiClimbType() {

        return Climbing.NONE;
    }

    @Override
    public Following aiFollowType() {

        return Following.AGRESSIVE;
    }

    @Override
    public Jumping aiJumpType() {

        return Jumping.BASIC;
    }

    @Override
    public Response aiResponseType() {

        return Response.TERITORIAL;
    }

    @Override
    public Stalking aiStalkType() {

        return Stalking.NONE;
    }

    @Override
    public Taming aiTameType() {

        return Taming.IMPRINTING;
    }

    @Override
    public Untaming aiUntameType() {

        return Untaming.STARVE;
    }

    @Override
    public Moving aiMovingType() {

        return Moving.WALK;
    }

    @Override
    public WaterAbility aiWaterAbilityType() {

        return WaterAbility.NONE;
    }

    @Override
    public boolean doesFlock() {

        return false;
    }

    @Override
    public Item getOrderItem() {

        return Items.bone;
    }

    public void updateSize() {

        double healthStep;
        double attackStep;
        double speedStep;
        healthStep = (this.maxHealth - this.baseHealth) / (this.getAdultAge() + 1);
        attackStep = (this.maxDamage - this.baseDamage) / (this.getAdultAge() + 1);
        speedStep = (this.maxSpeed - this.baseSpeed) / (this.getAdultAge() + 1);


        if (this.getDinoAge() <= this.getAdultAge()) {

            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(Math.round(this.baseHealth + (healthStep * this.getDinoAge())));
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(Math.round(this.baseDamage + (attackStep * this.getDinoAge())));
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.baseSpeed + (speedStep * this.getDinoAge()));

        }
    }

    @Override
    public int getAdultAge() {
        return 8;
    }

    public float getMaleSize() {
        return 1.2F;
    }
    
    public void onLivingUpdate(){
		super.onLivingUpdate();
		if(this.getAnimation() == this.animation_attack && this.getAnimationTick() == 12 && this.getAttackTarget() != null){
			this.attackEntityAsMob(this.getAttackTarget());
		}
	}


	public boolean attackEntityAsMob(Entity entity)
	{
		if(this.getAnimation() == NO_ANIMATION){
			this.setAnimation(animation_attack);
			return false;
		}
		if(this.getAnimation() == animation_attack && this.getAnimationTick() == 12){
			IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.attackDamage);
			boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)iattributeinstance.getAttributeValue());

			if (flag)
			{
				if(entity.ridingEntity != null){
					if(entity.ridingEntity  == this){
						entity.mountEntity(null);
					}
				}
				entity.motionY += (0.4000000059604645D / 2);
                knockbackEntity(entity, 0.05F, -1.1F);	

			}

			return flag;
		}
		return false;
	}
}