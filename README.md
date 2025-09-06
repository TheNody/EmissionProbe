# EmissionProbe  

[![GitHub](https://img.shields.io/badge/GitHub-Repo-blue?logo=github)](https://github.com/TheNody/EmissionProbe)
[![Issues](https://img.shields.io/github/issues/TheNody/EmissionProbe)](https://github.com/TheNody/EmissionProbe/issues)
[![Releases](https://img.shields.io/github/v/release/TheNody/EmissionProbe)](https://github.com/TheNody/EmissionProbe/releases)
[![License](https://img.shields.io/badge/License-GPLv3-green.svg)](LICENSE)

---

## 📖 О проекте

**EmissionProbe** — современное Android-приложение для инженеров-экологов и лабораторий, предназначенное для автоматизации расчётов параметров отбора проб промышленных выбросов.

Приложение позволяет:
- 🔢 **Автоматизировать вычисления** (диаметр наконечника, эквивалентный диаметр, L/De и др.)
- 📐 **Поддерживать различные сечения** (круглые и прямоугольные)
- 🌀 **Работать с разными типами фильтрации** (внутренняя и внешняя)
- 💾 **Сохранять историю расчётов** для последующего использования
- 📊 **Экспортировать отчёты** в формат Excel

**Главная цель** — сокращение времени на подготовку расчётов, минимизация ошибок и обеспечение соответствия нормативам (ГОСТ).

---

## 🛠️ Технологии

- **Kotlin** + **Jetpack Compose** — современный UI и навигация
- **MVVM + Clean Architecture** — чистая архитектура приложения
- **Hilt (Dagger)** — внедрение зависимостей
- **Room** — локальная база данных
- **Apache POI** — работа с Excel-файлами
- **mXparser** — математические вычисления
- **Coroutines + Flow** — асинхронные операции

---

## 📋 Требования

- **JDK 17** или выше
- **Android Studio Giraffe** или новее
- **Android Gradle Plugin 8.0**+
- **minSdk 26**, **targetSdk 36**
- Устройство/эмулятор с Android 8.0+

---

## 🚀 Установка и запуск

### Клонирование репозитория
```bash
git clone https://github.com/TheNody/EmissionProbe.git
cd EmissionProbe
```
### Открытие проекта в Android Studio
1. **Запустите Android Studio**
2. **Выберите** `File → Open`
3. **Укажите папку** с проектом `EmissionProbe`
4. **Дождитесь завершения** синхронизации Gradle и загрузки зависимостей

### Запуск приложения
1. **Подключите** Android-устройство или запустите эмулятор (API 26+)
2. **Нажмите** ▶ `Run` в Android Studio
3. **Выберите** целевое устройство

### Альтернативная сборка
```bash
# Сборка APK
./gradlew assembleDebug
```
```bash
# Установка на устройство
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## 📜 Лицензия

Проект распространяется под лицензией **GPL-3.0**.  
Вы можете свободно использовать, изменять и распространять код при условии соблюдения условий лицензии.

Полный текст лицензии доступен в файле [LICENSE](LICENSE).
