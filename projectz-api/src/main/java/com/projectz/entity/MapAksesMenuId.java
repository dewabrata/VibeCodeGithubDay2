package com.projectz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class MapAksesMenuId implements Serializable {
    private static final long serialVersionUID = -5338124991988435720L;
    @Column(name = "id_akses", nullable = false)
    private Long idAkses;

    @Column(name = "id_menu", nullable = false)
    private Long idMenu;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapAksesMenuId that = (MapAksesMenuId) o;
        return Objects.equals(idAkses, that.idAkses) && Objects.equals(idMenu, that.idMenu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAkses, idMenu);
    }
}
