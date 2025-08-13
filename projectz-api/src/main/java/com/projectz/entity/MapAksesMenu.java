package com.projectz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "map_akses_menu", schema = "projectz")
public class MapAksesMenu {
    @EmbeddedId
    private MapAksesMenuId id;

    @MapsId("idAkses")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_akses", nullable = false)
    private MstAkses akses;

    @MapsId("idMenu")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_menu", nullable = false)
    private MstMenu menu;
}
