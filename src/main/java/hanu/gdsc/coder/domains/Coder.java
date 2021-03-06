package hanu.gdsc.coder.domains;

import hanu.gdsc.share.domains.Id;

public class Coder {
    private Id id;
    private String name;
    private Url avatar;
    private Phone phone;
    private String university;
    private String slogan;
    private Gender gender;
    private String address;

    public Coder(Id id, String name, Url avatar, Phone phone, String university, String slogan, Gender gender, String address) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.phone = phone;
        this.university = university;
        this.slogan = slogan;
        this.gender = gender;
        this.address = address;
    }

    public static Coder create() {
        return new Coder(
                Id.generateRandom(),
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Id getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Url getAvatar() {
        return avatar;
    }

    public Phone getPhone() {
        return phone;
    }

    public String getUniversity() {
        return university;
    }

    public String getSlogan() {
        return slogan;
    }

    public Gender getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }
}
