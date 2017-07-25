#!/bin/sh

# скрипт предназначен для создания нового раздела диска microSDHC
# диск создается при первом включении в неразмеченной области,
# autor Alexander Demachev, project: berserk (site: https://berserk.tv)
#
# исходная разметка диска /dev/sdX при создании образа:
# где sdX - обобщенное название карты памяти microSDHC, точное название см. /etc/fstab
# первый раздел /dev/sdX1 => fat16 будет содержать ядро, загрузчик и драйверы
#                            для поддержки Raspberry Pi (dtb образы)
# второй раздел /dev/sdX2 => ext3  будет содержать корневую файловую систему, rootfs
#
# оставшиеся место не занято,
# и здесь будет создан третий раздел /dev/sdX3 для хранения мультимедиа информации
# после создания, раздел будет монтироваться в каталог /media/data
#
# последовательность действий:
# в каталоге /etc/rcS.d необходимо создать символическую ссылку S07first-run.sh
# ссылка на файл ../init.d/first-run.sh
#
FIRST_RUN="/first.bs.run"
DISK_DEV=/dev/mmcblk0
DISK_ROOTFS=/dev/mmcblk0p2
DISK_MEDIA=/dev/mmcblk0p3
MOUNT_MEDIA=/media/data


#######################################################################
# section func

check_need_create() {
    local disk="$1"
    # в случае отсутствия раздела, его необходимо создать
    if [ -b "$disk" ]; then return 1
    else return 0; fi
}

create_partition_fat32() {
    local dev_disk="$1"
    local dev2="$2"
    local dev3="$3"
    local name="DATA"
    local END2_MBYTE=`parted -s -m $dev_disk unit MB print | grep "^2:" | cut -d":" -f3 | tr "MB" " " | sed "s/\.[0-9]*//" `
    local START_MBYTE="$END2_MBYTE"
    #local START_MBYTE=`expr $END2_MBYTE + 1`
    local com_args="-s -a optimal $dev_disk mkpart primary fat32 $START_MBYTE 100% print quit"

    parted $com_args
    local code=$?
    sync
    if [ $? -eq 0 ]; then echo "SUCCESS => parted $com_args"
    else echo "FAIL => parted $com_args"; return 1; fi

    com_args="-F 32 -n $name $dev3"
    mkfs.vfat $com_args
    code=$?
    sync
    if [ $code -eq 0 ]; then echo "SUCCESS => mkfs.vfat $com_args"
    else echo "FAIL => mkfs.vfat $com_args"; fi
    return $code
}

# отключил изменение размера rootfs (ext3), так как операция resize2fs /dev/раздел занимает 6-7минут (размер раздела 3GB)
# как нибудь посмотрю время изменения раздела ext4 через ioctl EXT4_IOC_RESIZE_FS
#if [ -d "/mnt/.psplash" ]; then echo "MSG Resizing the disk space on the first running ..." > $fifo; fi
#resize_root_partition $DISK_DEV
resize_root_partition() {
    local dev_disk="$1"
    local full_size_byte=`fdisk -l $dev_disk | grep "${dev_disk}:" | cut -d" " -f5`
    if [ -z "$full_size_byte" ]; then return 1; fi

    local END2_BYTE=`parted -s -m $dev_disk unit B print | grep "^2:" | cut -d":" -f3 | tr -d "B" | sed "s/\.[0-9]*//"`
    local size_part=$(($full_size_byte-$END2_BYTE))
    if [ $? -ne 0 ] || [ -z "$size_part" ]; then return 2; fi

    local ten_persent=$(($size_part/10))
    if [ $? -ne 0 ] || [ -z "$ten_persent" ]; then return 3; fi

    local NEW_END2_BYTE=$(($END2_BYTE+$ten_persent))
    local com_args="-s -a optimal $dev_disk unit B resizepart 2 $NEW_END2_BYTE"
    parted $com_args
    local code=$?
    sync
    if [ $code -eq 0 ]; then echo "SUCCESS => parted $com_args"; return 0;
    else echo "FAIL => parted $com_args"; return 4; fi
}

mount_create_partition_fat32() {
    local dev="$1"
    local media="$2"
    local code=1
    if mount | grep -q "$media"; then echo "$dev already mount on $media, exit"; return 0; fi
    if [ ! -d "$media" ]; then mkdir $media;  fi

    sync
    com_args="-n -t vfat -o rw $dev $media"
    mount $com_args
    if mount | grep -q "$media"; then code=0;
    else mount $com_args; code=$?; fi

    if [ $code -eq 0 ]; then echo "SUCCESS => mount $com_args"
    else echo "FAIL => mount $com_args"; fi
    return $code
}

add_disk_in_fstab() {
    local disk="$1"
    local point="$2"
    # example
    #/dev/mmcblk0p     /media/card     auto       defaults,sync,noauto  0  0
    echo "$disk        $point          auto       defaults                           1  1" >> /etc/fstab
    sync
}

#######################################################################
# section start
# в корне раздела находиться файл по которому определяется первое включение устройcтва
fifo="/mnt/.psplash/psplash_fifo"
if [ ! -f "$FIRST_RUN" ]; then
    if [ -d "/mnt/.psplash" ]; then
        echo "MSG  ver 0.2.3 TORVIN     \"Along Came a Hammersmith\"" > $fifo
    fi
    exit 0
fi


# файл используется для вывода окна настройки сети во время первого запуска Kodi
touch "/tmp/$FIRST_RUN"

if check_need_create $DISK_MEDIA; then
    if create_partition_fat32 $DISK_DEV $DISK_ROOTFS $DISK_MEDIA; then
      if [ -d "/mnt/.psplash" ]; then echo "MSG Creating a new partition on the first running ..." > $fifo; fi
      if mount_create_partition_fat32 $DISK_MEDIA $MOUNT_MEDIA; then add_disk_in_fstab $DISK_MEDIA $MOUNT_MEDIA;
      else echo "ERROR_2, not mount_create_partition_fat32 $DISK_MEDIA $MOUNT_MEDIA"; fi
    else
      echo "ERROR_1, not create_partition_fat32 $DISK_DEV $DISK_ROOTFS $DISK_MEDIA"
    fi
fi

rm -f "$FIRST_RUN"
exit 0

