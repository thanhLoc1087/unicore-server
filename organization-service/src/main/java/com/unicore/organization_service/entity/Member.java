// package com.unicore.organization_service.entity;

// import java.sql.Date;
// import java.util.Set;

// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;

// import com.unicore.organization_service.enums.MemberGender;
// import com.unicore.organization_service.enums.MemberRole;

// import lombok.Data;

// @Data
// public abstract class Member {
//     @Id
//     @GeneratedValue(strategy = GenerationType.UUID)
//     protected String id;

//     protected String name;
//     protected Date dob;
//     protected MemberGender gender;
//     protected Set<MemberRole> roles;
// }
