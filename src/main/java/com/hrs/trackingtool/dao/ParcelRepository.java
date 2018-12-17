package com.hrs.trackingtool.dao;

import com.hrs.trackingtool.model.Parcel;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

// Access to table guests will be covered by this repository
public interface ParcelRepository extends CrudRepository<Parcel, Long> {

  // get parcel by tracking number
  Optional<Parcel> findByTrackingNumber(String trackingNumber);
  //get list of parcel by guest and parcel status(picked up or not)
  List<Parcel> findAllByGuestIdAndIsPickedUp(Long guestID,boolean isPickedUp);

}
