package nure.bielik.yehor.lab_task4;

import java.util.Date;

public class Note {

    private String name;
    private String description;
    private Date dateOfCreation;
    private Date dateOfAppointment;
    private static int lastId = 0;
    private int id;
    private NotesImportance importance;
    private String imageUri;

    public NotesImportance getImportance() {
        return importance;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setImportance(NotesImportance importance) {
        this.importance = importance;
    }

    public Note(String name, String description, Date dateOfCreation, int id, Date dateOfAppointment, NotesImportance importance, String imageUri) {
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
        this.dateOfAppointment = dateOfAppointment;
        this.importance = importance;
        this.id = id;
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
    }


    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Note(String name, String description, Date dateOfCreation, Date dateOfAppointment, NotesImportance importance, String imageUri) {
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
        this.dateOfAppointment = dateOfAppointment;
        id = ++lastId;
        this.importance = importance;
        this.imageUri = imageUri;
    }
}
