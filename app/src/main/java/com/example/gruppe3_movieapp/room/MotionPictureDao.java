package com.example.gruppe3_movieapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gruppe3_movieapp.model.MotionPicture;

import java.util.List;

/**
 * @author Mustafa
 */
@Dao
public interface MotionPictureDao {
    @Query("SELECT * FROM motionpicture")
    List<MotionPicture> getAll();

    /**
     Holt Titel mit der/den gesuchten ID/s
     @param imdbId String-Array mit einem oder mehreren IDs
     @return Alle Datensätze die dem Suchkriterium entsprechen
     */
    @Query("SELECT * FROM motionpicture WHERE imdbId IN (:imdbId)")
    List<MotionPicture> getMotionPicture(String... imdbId);

    /**
     Speichert mehrere Objekte in der Datenbank. Wenn Datensaatz bereits vorhanden, wird ersetzt.
     @param motionPictures Liste der einzufügenden Objekte
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MotionPicture... motionPictures);

    /**
     Löscht einzelnes Objekt aus der Datenbank
     @param motionPicture Zu löschendes Objekt
     */
    @Delete
    void delete(MotionPicture motionPicture);

    /**
     Aktualisiert ein Objekt in der DB. Wenn nicht in DB vorhanden, passiert nichts.
     @param motionPictures Ein oder mehrere Objekte welche in der DB aktualisiert werden sollen.
     */
    @Update
    void update(MotionPicture... motionPictures);

}
