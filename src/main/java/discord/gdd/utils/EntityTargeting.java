package discord.gdd.utils;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.google.common.base.Preconditions;

/**
 * 
 * An utility class to help get what an entity is looking at
 *
 */
public final class EntityTargeting {

	/**
	 * The default search radius, used if none is specified
	 */
	public static final double DEFAULT_RADIUS = 50.0;

	/**
	 * The default target offset, used if none is specified
	 */
	public static final double DEFAULT_OFFSET = 1.0;

	private EntityTargeting() {}

	/**
	 * Scans and returns the first <code>Player</code> that
	 * <code>observer</code> is looking at
	 * 
	 * @param observer
	 *            the entity observing
	 * @return the <code>Player</code> being observed, or null if none is
	 * @throws NullPointerException
	 *             if <code>observer</code> is <code>null</code>
	 */
	public static Player getTargetPlayer(LivingEntity observer) {
		return getTargetPlayer(observer, DEFAULT_RADIUS);
	}

	/**
	 * Scans and returns the first <code>Player</code> that
	 * <code>observer</code> is looking at
	 * 
	 * @param observer
	 *            the entity observing
	 * @param searchRadius
	 *            the radius for the entity scan
	 * @return the <code>Player</code> being observed, or null if none is
	 * @throws NullPointerException
	 *             if <code>observer</code> is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if <code>searchRadius</code> is negative
	 */
	public static Player getTargetPlayer(LivingEntity observer, double searchRadius) {
		return getTargetPlayer(observer, searchRadius, DEFAULT_RADIUS);
	}

	/**
	 * Scans and returns the first <code>Player</code> that
	 * <code>observer</code> is looking at
	 * 
	 * @param observer
	 *            the entity observing
	 * @param searchRadius
	 *            the radius for the entity scan
	 * @param targetOffset
	 *            the maximum distance between the line of sight and entity
	 *            being observed
	 * @return the <code>Player</code> being observed, or null if none is
	 * @throws NullPointerException
	 *             if <code>observer</code> is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if <code>searchRadius</code> or <code>targetOffset</code> are
	 *             negative
	 */
	public static Player getTargetPlayer(LivingEntity observer, double searchRadius, double targetOffset) {
		return (Player) getTargetEntity(observer, EnumSet.of(EntityType.PLAYER), searchRadius, targetOffset);
	}

	/**
	 * Scans and returns the first entity that <code>observer</code> is looking
	 * at
	 * 
	 * @param observer
	 *            the entity observing
	 * @return the entity being observed, or null if none is
	 * @throws NullPointerException
	 *             if <code>observer</code> is <code>null</code>
	 * 
	 */
	public static Entity getTargetEntity(LivingEntity observer) {
		return getTargetEntity(observer, DEFAULT_RADIUS);
	}

	/**
	 * Scans and returns the first entity that <code>observer</code> is looking
	 * at
	 * 
	 * @param observer
	 *            the entity observing
	 * @param searchRadius
	 *            the radius for the entity scan
	 * @return the entity being observed, or null if none is
	 * @throws NullPointerException
	 *             if <code>observer</code> or <code>targetTypes</code> are
	 *             <code>null</code>
	 * @throws IllegalArgumentException
	 *             if <code>searchRadius</code> is negative
	 */
	public static Entity getTargetEntity(LivingEntity observer, double searchRadius) {
		return getTargetEntity(observer, searchRadius, DEFAULT_OFFSET);
	}

	/**
	 * Scans and returns the first entity that <code>observer</code> is looking
	 * at
	 * 
	 * @param observer
	 *            the entity observing
	 * @param searchRadius
	 *            the radius for the entity scan
	 * @param targetOffset
	 *            the maximum distance between the line of sight and entity
	 *            being observed
	 * @return the entity being observed, or null if none is
	 * @throws NullPointerException
	 *             if <code>observer</code> is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if <code>searchRadius</code> or <code>targetOffset</code> are
	 *             negative
	 */
	public static Entity getTargetEntity(LivingEntity observer, double searchRadius, double targetOffset) {
		return getTargetEntity(observer, EnumSet.allOf(EntityType.class), searchRadius, targetOffset);
	}

	/**
	 * Scans and returns the first sub-type of <code>targetClass</code> that
	 * <code>observer</code> is looking at
	 * 
	 * @param observer
	 *            the entity observing
	 * @param targetClass
	 *            the target type of the scanned entities
	 * @throws NullPointerException
	 *             if <code>observer</code> is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if <code>searchRadius</code> is negative
	 */
	public static <T extends Entity> T getTargetEntity(LivingEntity observer, Class<T> targetClass) {
		return getTargetEntity(observer, targetClass, DEFAULT_RADIUS);
	}

	/**
	 * Scans and returns the first sub-type of <code>targetClass</code> that
	 * <code>observer</code> is looking at
	 * 
	 * @param observer
	 *            the entity observing
	 * @param targetClass
	 *            the target type of the scanned entities
	 * @param searchRadius
	 *            the radius for the entity scan
	 * @throws NullPointerException
	 *             if <code>observer</code> or <code>targetClass</code> are
	 *             <code>null</code>
	 * @throws IllegalArgumentException
	 *             if <code>searchRadius</code> is negative
	 */
	public static <T extends Entity> T getTargetEntity(LivingEntity observer, Class<T> targetClass,
			double searchRadius) {
		return getTargetEntity(observer, targetClass, searchRadius, DEFAULT_OFFSET);
	}

	/**
	 * Scans and returns the first sub-type of <code>targetClass</code> that
	 * <code>observer</code> is looking at
	 * 
	 * @param observer
	 *            the entity observing
	 * @param targetClass
	 *            the target type of the scanned entities
	 * @param searchRadius
	 *            the radius for the entity scan
	 * @param targetOffset
	 *            the maximum distance between the line of sight and entity
	 *            being observed
	 * @throws NullPointerException
	 *             if <code>observer</code> or <code>targetClass</code> are
	 *             <code>null</code>
	 * @throws IllegalArgumentException
	 *             if <code>searchRadius</code> or <code>targetOffset</code> are
	 *             negative
	 */
	public static <T extends Entity> T getTargetEntity(LivingEntity observer, Class<T> targetClass, double searchRadius,
			double targetOffset) {
		Preconditions.checkNotNull(targetClass);

		Set<EntityType> targetTypes = Stream.of(EntityType.values())
				.filter(targetType -> targetClass.isAssignableFrom(targetType.getEntityClass()))
				.collect(Collectors.toSet());

		return targetClass.cast(getTargetEntity(observer, targetTypes, searchRadius, targetOffset));
	}

	/**
	 * Scans and returns the first entity of type contained in
	 * <code>targetTypes</code> that <code>observer</code> is looking at
	 * 
	 * @param observer
	 *            the entity observing
	 * @param targetTypes
	 *            the <code>EntityType</code>s being scanned
	 * @return the entity being observed, or null if none is
	 * @throws NullPointerException
	 *             if <code>observer</code> is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if <code>searchRadius</code> is negative
	 */
	public static Entity getTargetEntity(LivingEntity observer, Collection<EntityType> targetTypes) {
		return getTargetEntity(observer, targetTypes, DEFAULT_RADIUS);
	}

	/**
	 * Scans and returns the first entity of type contained in
	 * <code>targetTypes</code> that <code>observer</code> is looking at
	 * 
	 * @param observer
	 *            the entity observing
	 * @param targetTypes
	 *            the <code>EntityType</code>s being scanned
	 * @param searchRadius
	 *            the radius for the entity scan
	 * @return the entity being observed, or null if none is
	 * @throws NullPointerException
	 *             if <code>observer</code> or <code>targetTypes</code> are
	 *             <code>null</code>
	 * @throws IllegalArgumentException
	 *             if <code>searchRadius</code> is negative
	 */
	public static Entity getTargetEntity(LivingEntity observer, Collection<EntityType> targetTypes,
			double searchRadius) {
		return getTargetEntity(observer, targetTypes, searchRadius, DEFAULT_OFFSET);
	}

	/**
	 * Scans and returns the first entity of type contained in
	 * <code>targetTypes</code> that <code>observer</code> is looking at
	 * 
	 * @param observer
	 *            the entity observing
	 * @param targetTypes
	 *            the <code>EntityType</code>s being scanned
	 * @param searchRadius
	 *            the radius for the entity scan
	 * @param targetOffset
	 *            the maximum distance between the line of sight and entity
	 *            being observed
	 * @return the entity being observed, or null if none is
	 * @throws NullPointerException
	 *             if <code>observer</code> or <code>targetTypes</code> are
	 *             <code>null</code>
	 * @throws IllegalArgumentException
	 *             if <code>searchRadius</code> or <code>targetOffset</code> are
	 *             negative
	 */
	public static Entity getTargetEntity(LivingEntity observer, Collection<EntityType> targetTypes, double searchRadius,
			double targetOffset) {
		Preconditions.checkNotNull(observer);
		Preconditions.checkNotNull(targetTypes);

		Preconditions.checkArgument(searchRadius >= 0);
		Preconditions.checkArgument(targetOffset >= 0);

		double halfRadius = searchRadius / 2;
		List<Entity> targets = observer.getNearbyEntities(halfRadius, halfRadius, halfRadius);

		Location playerLoc = observer.getEyeLocation();

		Vector linePoint = playerLoc.toVector();
		Vector lineDirection = playerLoc.getDirection();

		for (Entity target : targets) {
			if (!targetTypes.contains(target.getType())) {
				continue;
			}

			Vector planePoint = target.getLocation().toVector();
			Vector planeNormal = linePoint.clone().subtract(planePoint);

			double t = (planePoint.dot(planeNormal) - planeNormal.dot(linePoint)) / planeNormal.dot(lineDirection);
			if (t < 0) {
				continue;
			}

			Vector intersection = lineDirection.clone().multiply(t).add(linePoint);
			if (intersection.distanceSquared(planePoint) < Math.pow(targetOffset, 2)) {
				return target;
			}
		}

		return null;
	}
}
