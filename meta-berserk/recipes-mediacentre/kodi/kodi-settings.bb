DESCRIPTION = "settings for KODI Media Center"
SECTION = "configs"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

FILESEXTRAPATHS_prepend := "${THISDIR}/kodi:"
                    
S = "${WORKDIR}"

require recipes-berserk/tv/tv-dir.inc

KODI_HOME_DIR = "/home/root/.kodi"    
USERDATA = "${KODI_HOME_DIR}/userdata"
PVR_IPTVSIMPLE_SETTINGS = "${USERDATA}/addon_data/pvr.iptvsimple"


SRC_URI += " \
    file://settings/guisettings.xml \
    file://settings/settings-pvr-iptvsimple.xml \
    file://settings/advancedsettings.xml \
    "



PACKAGES = "${PN}"

FILES_${PN} +=  "${USERDATA} \
                "

FIND_M3U_PATH = "m3uPath. value=."
REPLACE_M3U_PATH = "${TV_CONFIG_DIR}/${TV_CONFIG}"
                
do_install() {
    # Kodi guisettings
    install -d "${D}${USERDATA}"
    install -m 0644 "${S}/settings/guisettings.xml" "${D}${USERDATA}"
    install -m 0644 "${S}/settings/advancedsettings.xml" "${D}${USERDATA}"
    
    # addon pvr.iptvsimple settings
    install -d "${D}${PVR_IPTVSIMPLE_SETTINGS}"
    
    # change path show tv-config.bb
    sed -i "s|${FIND_M3U_PATH}|&${REPLACE_M3U_PATH}|" "${S}/settings/settings-pvr-iptvsimple.xml" 
    install -m 0644 "${S}/settings/settings-pvr-iptvsimple.xml" "${D}${PVR_IPTVSIMPLE_SETTINGS}/settings.xml"
}
        

        
       
    
  
