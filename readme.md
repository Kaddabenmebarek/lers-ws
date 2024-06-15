# WSLabInventory

Service used to retrieve all the stored data needed for the client.

## Project maintainers
- Kadda Benmebarek

## Getting started with WSLabInventory

### IDE Configuration
Import the WSLabInventory directory as a maven project (and not a gradle project).

### Maven configuration
The maven project requires you to have a valid configuration of the internal repositories URLs.   
You may need to edit your `~/.m2/settings.xml` to include them. See a configuration example in `settings_example.xml`.

### Start WSLabInventory
Launch the main that is on the class `org.research.kadda.labinventory.LabInventoryApplication`.

If you are workin on local, make sure that the argument 
`-Dmode=dev` is set.

## JAR PATH
Once built, make sure to upload the jar file in this folder (same path on ares or apollo):

`/u00/wsosiris/wsosiris.jar`

## SERVICE TO LAUNCH

`wslabinventory.service`.

`$ sudo systemctl start wslabinventory`.

`$ sudo systemctl status wslabinventory`.

## DETAILS
URL: `http://apollo:9980/lers/` or `http://ares:9980/lers/`.

SWAGGER: `http://apollo:9980/lers/swagger-ui/index.html` or `http://ares:9980/lers/swagger-ui/index.html`.

