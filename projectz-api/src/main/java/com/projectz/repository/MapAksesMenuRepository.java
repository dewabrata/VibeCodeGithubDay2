package com.projectz.repository;

import com.projectz.entity.MapAksesMenu;
import com.projectz.entity.MapAksesMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapAksesMenuRepository extends JpaRepository<MapAksesMenu, MapAksesMenuId> {
}
