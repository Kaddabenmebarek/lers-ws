package org.research.kadda.labinventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author: Kadda
 */

@Entity
@Table(schema = "CHEMINFRA", name = "GROUPS")
public class Group {
    @Id
    String groupname;

    @Column
    String member;

    public Group() {
    }

    public String getGroupName() {
        return groupname;
    }

    public void setGroupName(String groupname) {
        this.groupname = groupname;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}
