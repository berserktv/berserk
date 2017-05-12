# дополнительный параметры конфигурации описываются в rpbi.cfg
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://db.txt.patch;patch=1 \
            file://rbpi.cfg \
            "

# в BSP слое meta-raspberrypi не работают фрагменты конфигураций
# https://github.com/agherzan/meta-raspberrypi/issues/14
# поэтому делаю напрямую
# в методе do_kernel_configme конфигурация ядра копируется из базы архитектур arch/ в рабочий каталог
do_kernel_configme_append() {
    cat ${WORKDIR}/rbpi.cfg >> ${WORKDIR}/defconfig
}

# CMDLINE for raspberrypi
# default CMDLINE = "dwc_otg.lpm_enable=0 console=serial0,115200 root=/dev/mmcblk0p2 rootfstype=ext4 rootwait"
CMDLINE = "quiet dwc_otg.lpm_enable=0 console=serial0,115200 root=/dev/mmcblk0p2 rootfstype=ext4 rootwait"
