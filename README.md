# LoftMoney
Мобильное приложение для учета финансов.


| <img src="app/1.png"> | <img src="app/2.png"> | <img src="app/3.png"> |
| ---------------------------------------------- | -------------------------------------------- | ------------------------------------------- |

## Из чего состоит

Приложение состоит из двух activity - активити добавление новых расходов/доходов и активити основного экрана.

На основном экране 2 фрагмента, они связаны между собой **TabLayout**'ом и **ViewPager**'ом.

К одному из фрагментов подвешен **RecyclerView**

Диаграмма финансов создавалась с помощью **CustomView**

### Технологии, используемые в проекте
**gradle** - для сборки проекта.
**retrofit**, **okhttp**, **rxjava2** - для работы с сетью.

### Как запустить
Способ №1.
Скачайте файл app-mobile-debug.apk и установите на ваше устройство

Способ №2.
Чтобы запустить приложение требуется android studio и android emulator.

1. Склонируйте проект.
2. Откройте его в Android Studio.
3. Запускайте, нажав на "Run App" в верхней панели инструментов.
