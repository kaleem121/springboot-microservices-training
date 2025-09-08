# Day 5 – Profiles & Logging

- **Profiles:**
  - dev → MySQL localhost:3306, `ddl-auto=update`, SQL shown
  - prod → `ddl-auto=validate` (no schema changes), quieter logs
  - Run: `--spring.profiles.active=dev|prod`(RunAs ->Config->program argument)
- **Logging:**
  - SLF4J in services (`info/debug/warn`)
  - File: `logs/app.log`
- Swagger (optional): enabled in dev, disabled in prod
