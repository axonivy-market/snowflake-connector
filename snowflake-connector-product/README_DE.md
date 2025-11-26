# Snowflake SQL API

Snowflake ist eine moderne, cloudbasierte Datenplattform, die skalierbares und
flexibles Data Warehousing bietet und eine effiziente Speicherung, Abfrage und
Analyse großer Mengen strukturierter und semistrukturierter Daten über mehrere
Cloud-Anbieter hinweg ermöglicht, darunter AWS, Azure und Google Cloud.

Dieser Konnektor:

- unterstützt Sie mit einer Demo-Implementierung, um Ihren Integrationsaufwand
  zu reduzieren.
- gibt Ihnen die volle Kontrolle über die [Snowflake SQL
  API](https://docs.snowflake.com/developer-guide/sql-api/index?_fsi=io7jNW4M&_fsi=io7jNW4M#limitations-of-the-sql-api).
- Ermöglicht Ihnen die Ausführung von SQL-Anweisungen in den Datenbanken von
  Snowflake.
- Ermöglicht Ihnen die Überprüfung des Ausführungsstatus von SQL-Anweisungen in
  den Datenbanken von Snowflake.
- Ermöglicht Ihnen, die Ausführung von SQL-Anweisungen in den
  Snowflake-Datenbanken abzubrechen.

## Demo

1. Starten Sie den Prozess „ `AccessToSnowflakeDemo/startDemo.ivp”.`
2. Geben Sie die SQL-Anweisung ein und klicken Sie auf die Schaltfläche „ `“
   (SQL-Anweisung ausführen) „Execute SQL Statement“ (SQL-Anweisung ausführen)
   „` “ (SQL-Anweisung ausführen) auf der Registerkarte „ `“ (SQL-Anweisung
   ausführen) „` “ (SQL-Anweisung ausführen) Das Ergebnis wird im
   Ergebnisbereich angezeigt:
- Mit asynchroner Abfrage:
  ![async-query](images/execute-sql-statement-with-async.png)
- Ohne asynchrone Abfrage:
  ![non-async-query](images/execute-sql-statement-without-async.png)
3. Geben Sie den Statement-Handle ein und klicken Sie auf die Schaltfläche „ `“
   (SQL-Statement-Ausführungsstatus überprüfen).` auf der Registerkarte „ `“
   (SQL-Statement-Ausführungsstatus überprüfen).` Das Ergebnis wird im
   Ergebnisbereich angezeigt:
   ![check-sql-statement-execution-status](images/check-sql-statement-execution-status.png)
4. Geben Sie den Statement-Handle ein und klicken Sie auf die Schaltfläche „ `“
   (SQL-Anweisung ausführen). „Cancel SQL Statement Execution“ (SQL-Anweisung
   abbrechen)` auf der Registerkarte „ `“ (SQL-Anweisung abbrechen)` Das
   Ergebnis wird im Ergebnisbereich angezeigt:
   ![cancel-sql-statement-execution](images/cancel-sql-statement-execution.png)

## Setup

1. Registrieren Sie sich für ein [Snowflake-Konto](https://signup.snowflake.com)
2. Melden Sie sich bei Snowflake an.
3. Gehen Sie im linken Menü zu „Admin > Accounts“, um die Werte für „ `“
   (Lokalisierungs-URL)` und „ `“ (Lokalisierungs-URL)` zu erfassen.
   ![get-locator](images/get-locator.png)
4. Siehe [Privaten Schlüssel
   generieren](https://docs.snowflake.com/en/user-guide/key-pair-auth#generate-the-private-key),
   um eine unverschlüsselte Version zu generieren.
5. Siehe [Öffentlichen Schlüssel
   generieren](https://docs.snowflake.com/en/user-guide/key-pair-auth#generate-a-public-key),
   um den öffentlichen Schlüssel unter Verwendung des privaten Schlüssels zu
   generieren.
6. Siehe [Zuweisen des öffentlichen
   Schlüssels](https://docs.snowflake.com/en/user-guide/key-pair-auth#assign-the-public-key-to-a-snowflake-user),
   um den öffentlichen Schlüssel einem Snowflake-Benutzer zuzuweisen.
> [!HINWEIS] Sie können den Fingerabdruck des öffentlichen Schlüssels
> überprüfen, indem Sie [den Fingerabdruck des öffentlichen Schlüssels des
> Benutzers
> überprüfen](https://docs.snowflake.com/en/user-guide/key-pair-auth#verify-the-user-s-public-key-fingerprint)
> 7. Aktualisieren Sie Ihre `variables.yaml` [!HINWEIS] Der Benutzername muss
> mit dem Benutzernamen im Snowflake-Profil übereinstimmen.

```
@variables.yaml@
```
