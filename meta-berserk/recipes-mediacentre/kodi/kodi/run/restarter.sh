#!/bin/sh

# Very Simple Restarter
SIGSEGV=139
SIGINT=130

# меню Kodi:
# Выход
# Выключить систему
# Выключение по таймеру
# перезагрузка
KODI_EXIT=0
KODI_POWEROFF=64
KODI_TIMER=0
KODI_REBOOT=66


while /bin/true; do
  $1 $2 $3 $4 $5
  rc=$?
  if [ $rc = $SIGSEGV ]; then
    echo "Segmentation fault :)"
  else
    echo "Return code $rc"
  fi
  sleep 1

  if [ $rc -eq $KODI_EXIT ]; then poweroff;
  elif [ $rc -eq $KODI_POWEROFF ]; then poweroff;
  elif [ $rc -eq $KODI_TIMER ]; then poweroff;
  elif [ $rc -eq $KODI_REBOOT ]; then reboot;
  fi

  if [ $rc = $SIGINT ]; then
    echo "Quitting"
    break
  fi
done
