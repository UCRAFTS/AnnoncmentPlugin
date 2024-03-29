package net.ucrafts.announcement.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ConfigType {

    DB_UPDATED_PERIOD("db.updatePeriod"),
    DB_TABLES_PREFIX("db.tablesPrefix"),
    SERVER("server"),
    RELOAD_MESSAGE("reload"),
    FEATURE_AD_ENABLE("features.ad.enable"),
    FEATURE_AD_PERIOD("features.ad.period"),
    FEATURE_DEATH_ENABLE("features.death.enable"),
    FEATURE_DEATH_PVP_RADIUS_NOTIFY("features.death.pvp.radius"),
    FEATURE_DEATH_PVP_FORMAT("features.death.pvp.format"),
    FEATURE_DEATH_LOCATION_FORMAT("features.death.location.format"),
    FEATURE_JOIN_ENABLE("features.join.enable"),
    FEATURE_JOIN_ACTION_BAR("features.join.actionBar"),
    FEATURE_JOIN_FORMAT("features.join.format"),
    FEATURE_JOIN_LEFT_FORMAT("features.join.leftFormat"),
    FEATURE_FEATURE_HIDE_ACHIEVEMENTS_ENABLE("features.hideAchievements.enable");

    private final String name;

}
