package hu.bme.mit.spaceship;

/**
* A simple spaceship with two proton torpedos and four lasers
*/
public class GT4500 implements SpaceShip {

  private TorpedoStore primaryTorpedoStore;
  private TorpedoStore secondaryTorpedoStore;

  private boolean wasPrimaryFiredLast = false;

  public GT4500() {
    this.primaryTorpedoStore = new TorpedoStore(10);
    this.secondaryTorpedoStore = new TorpedoStore(10);
  }


  @Override
  public boolean fireLasers(FiringMode firingMode) {
    // TODO not implemented yet
    return false;
  }

  /**
  * Tries to fire the torpedo stores of the ship.
  *
  * @param firingMode how many torpedo bays to fire
  * 	SINGLE: fires only one of the bays.
  * 			- For the first time the primary store is fired.
  * 			- To give some cooling time to the torpedo stores, torpedo stores are fired alternating.
  * 			- But if the store next in line is empty the ship tries to fire the other store.
  * 			- If the fired store reports a failure, the ship does not try to fire the other one.
  * 	ALL:	tries to fire both of the torpedo stores.
  *
  * @return whether at least one torpedo was fired successfully
  */
  @Override
  public boolean fireTorpedos(FiringMode firingMode) {

    boolean firingSuccess = false;

    switch (firingMode) {
      case SINGLE:
        if (wasPrimaryFiredLast) {
          // try to fire the secondary first
          if (!firingSuccess) firingSuccess = fireSecondary();

	  // although primary was fired last time, but the secondary is empty
	  // thus try to fire primary again
	  if (!firingSuccess) firingSuccess = firePrimary();
	  // if both of the stores are empty, nothing can be done, return failure
        }
        else {
          // try to fire the primary first
	  if (!firingSuccess) firingSuccess = firePrimary();

          // although secondary was fired last time, but primary is empty
          // thus try to fire secondary again
          if (!firingSuccess) firingSuccess = fireSecondary();


          // if both of the stores are empty, nothing can be done, return failure
        }
        break;

      case ALL:
        // try to fire both of the torpedos
        firingSuccess = primaryTorpedoStore.fire(1) && secondaryTorpedoStore.fire(1) ;
	wasPrimaryFiredLast = false;
        break;
    }

    return firingSuccess;
  }

  private boolean firePrimary(){
    if (! primaryTorpedoStore.isEmpty()) {
      wasPrimaryFiredLast = true;
      return primaryTorpedoStore.fire(1);
    }

    return false;
  }

  private boolean fireSecondary(){
    if (! secondaryTorpedoStore.isEmpty()) {
      wasPrimaryFiredLast = false;
      return secondaryTorpedoStore.fire(1);
    }
    return false;
  }
}
