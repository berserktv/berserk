DESCRIPTION = "settings for KODI Media Center"
SECTION = "configs"
PR = "r1"
LICENSE = "MIT"
MD5_SUM = "md5=0835ade698e0bcf8506ecda2f7b4f302"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;${MD5_SUM}"


FILESEXTRAPATHS_prepend := "${THISDIR}/kodi:"
                    
S = "${WORKDIR}"

require kodi-dir.inc
require recipes-berserk/tv/tv-dir.inc

PVR_IPTVSIMPLE_SETTINGS = "${KODI_USERDATA}/addon_data/pvr.iptvsimple"
VIDEO_YOUTUBE_SETTINGS = "${KODI_USERDATA}/addon_data/plugin.video.youtube"


SRC_URI += " \
    file://settings/guisettings.xml \
    file://settings/settings-pvr-iptvsimple.xml \
    file://settings/advancedsettings.xml \
    file://settings/settings-video-youtube.xml \
    "



PACKAGES = "${PN}"

FILES_${PN} +=  "${KODI_USERDATA} \
                "

FIND_M3U_PATH = "m3uPath. value=."
REPLACE_M3U_PATH = "${TV_CONFIG_DIR}/${TV_CONFIG}"
                
do_install() {
    # Kodi guisettings
    install -d "${D}${KODI_USERDATA}"
    install -m 0644 "${S}/settings/guisettings.xml" "${D}${KODI_USERDATA}"
    install -m 0644 "${S}/settings/advancedsettings.xml" "${D}${KODI_USERDATA}"
    
    # addon pvr.iptvsimple and plugin.video.youtube
    install -d "${D}${PVR_IPTVSIMPLE_SETTINGS}"
    install -d "${D}${VIDEO_YOUTUBE_SETTINGS}"
    
    # change path show tv-config.bb
    sed -i "s|${FIND_M3U_PATH}|&${REPLACE_M3U_PATH}|" "${S}/settings/settings-pvr-iptvsimple.xml" 
    install -m 0644 "${S}/settings/settings-pvr-iptvsimple.xml" "${D}${PVR_IPTVSIMPLE_SETTINGS}/settings.xml"
    install -m 0644 "${S}/settings/settings-video-youtube.xml" "${D}${VIDEO_YOUTUBE_SETTINGS}/settings.xml"
}
        

        
       
    
  
