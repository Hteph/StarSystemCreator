package com.github.hteph.GUI;

import com.github.hteph.ObjectsOfAllSorts.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public final class Pagemaker {

    private Pagemaker() {
        //No instances of this class please!

    }

    //Methods------------------------------------------------
    public static VBox generator(StellarObject target) {
        VBox page = new VBox();
        if (target instanceof Star) page = PageGenerator((Star) target);
        if (target instanceof Planet) page = pageGenerator((Planet) target);
        if (target instanceof Jovian) page = pageGenerator((Jovian) target);
        if (target instanceof AsteroidBelt) page = PageGenerator((AsteroidBelt) target);
        return page;
    }

    //Inner Methods -----------------------------------------------
    private static VBox PageGenerator(AsteroidBelt asteroidBelt) {
        VBox infoPage = new VBox();

        Text titelInfo = new Text("Asteroid Belt");

        titelInfo.setFont(Font.font("Verdana", 20));
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().add(titelInfo);
        topBox.setPadding(new Insets(15, 12, 15, 12));

        infoPage.getChildren().add(topBox);

        TextArea description = new TextArea(asteroidBelt.getDescription());
        description.setPrefColumnCount(60);
        description.setWrapText(true);

        Rectangle planetSphere = new Rectangle();
        planetSphere.setTranslateX(200);
        planetSphere.setTranslateY(150);


        Color ambiColor = new Color(1.0, 1.0, 1.0, 0.5);

        AmbientLight light = new AmbientLight();
        light.setColor(ambiColor);

        PointLight light2 = new PointLight();
        light2.setColor(Color.WHITE);

        BackgroundImage myBI = getBackgroundImage("/Starfield.png");

        Group displayPlanet = new Group(planetSphere, light, light2);


        HBox pictBox = new HBox(displayPlanet);

        pictBox.setBackground(new Background(myBI));

        HBox introPlanet = new HBox(description, pictBox);
        introPlanet.setPadding(new Insets(15, 12, 15, 12));
        introPlanet.setSpacing(10);

        infoPage.getChildren().add(introPlanet);

        //making the first fact pane
        TitledPane firstTitledPane = new TitledPane();
        firstTitledPane.setText("Basic Facts");

        ListView<String> factList = new ListView<>();

        ObservableList<String> jovianFacts = FXCollections.observableArrayList("Asteroidbelt Type: " + asteroidBelt.getAsterioidBeltType(),
                                                                               "Asteroidbelt width [AU]:"
                                                                                       + String.valueOf(asteroidBelt.getAsteroidBeltWidth()));

        factList.setItems(jovianFacts);
        factList.maxWidth(50);
        factList.setPrefHeight(100);

        VBox factBox = new VBox();

        factBox.getChildren().add(factList);
        factBox.setPadding(new Insets(15, 12, 15, 12));

        firstTitledPane.setContent(factBox);

        Accordion furtherFacts = new Accordion();
        furtherFacts.getPanes().addAll(firstTitledPane);

        infoPage.getChildren().add(furtherFacts);

        return infoPage;
    }


    private static VBox pageGenerator(Jovian jovian) {
        VBox infoPage = new VBox();

        Text titelInfo = new Text(jovian.getName() + " (" + jovian.getClassificationName() + ")");

        titelInfo.setFont(Font.font("Verdana", 20));
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().add(titelInfo);
        topBox.setPadding(new Insets(15, 12, 15, 12));

        infoPage.getChildren().add(topBox);

        TextArea description = new TextArea(jovian.getDescription());
        description.setPrefColumnCount(60);
        description.setWrapText(true);

        Sphere planetSphere = new Sphere(100);
        planetSphere.setTranslateX(200);
        planetSphere.setTranslateY(150);

        //Set planet picture
        final PhongMaterial jovianColour = new PhongMaterial();
        findObjectColour(jovian, jovianColour);
        planetSphere.setMaterial(jovianColour);
        jovianColour.setBumpMap(new Image("/normalmap.png"));
        AmbientLight light = new AmbientLight();
        light.setColor(new Color(1.0, 1.0, 1.0, 0.5));
        PointLight light2 = new PointLight();
        light2.setColor(Color.WHITE);
        Group displayPlanet = new Group(planetSphere, light, light2);

        HBox pictBox = new HBox(displayPlanet);
        pictBox.setBackground(new Background(getBackgroundImage("/Starfield.png")));
        HBox introPlanet = new HBox(description, pictBox);
        introPlanet.setPadding(new Insets(15, 12, 15, 12));
        introPlanet.setSpacing(10);
        infoPage.getChildren().add(introPlanet);

        //making the first fact pane
        ObservableList<String> jovianFacts = FXCollections.observableArrayList("Mass [Earth-eqv]: " + jovian.getMass(),
                                                                               "Orbital period [Earth-years]: " + jovian.getOrbitalPeriod());

        TitledPane firstTitledPane = getTitledPane("Basic Facts",jovianFacts);

        Accordion furtherFacts = new Accordion();
        furtherFacts.getPanes().addAll(firstTitledPane);

        infoPage.getChildren().add(furtherFacts);

        return infoPage;
    }

    private static BackgroundImage getBackgroundImage(String backgroundPicture) {
        return new BackgroundImage(new Image(backgroundPicture,
                                             833,
                                             833,
                                             false,
                                             true),
                                   BackgroundRepeat.REPEAT,
                                   BackgroundRepeat.NO_REPEAT,
                                   BackgroundPosition.DEFAULT,
                                   BackgroundSize.DEFAULT);
    }

    private static VBox pageGenerator(Planet planet) {

        VBox infoPage = new VBox();

        Text titelInfo = new Text(planet.getName() + " (" + planet.getClassificationName() + ")");

        titelInfo.setFont(Font.font("Verdana", 20));
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().add(titelInfo);
        topBox.setPadding(new Insets(15, 12, 15, 12));

        infoPage.getChildren().add(topBox);

        TextArea description = new TextArea(planet.getDescription());
        description.setPrefColumnCount(60);
        description.setWrapText(true);

        HBox pictBox = new HBox(getSphere(planet, new Image("/normalmap.png")));
        pictBox.setBackground(new Background(getBackgroundImage("/Starfield.png")));

        HBox introPlanet = new HBox(description, pictBox);
        introPlanet.setPadding(new Insets(15, 12, 15, 12));
        introPlanet.setSpacing(10);

        infoPage.getChildren().add(introPlanet);
        //making the first fact pane

        ObservableList<String> starFacts = FXCollections.observableArrayList(
                "Gravity [Earth-eqv]: " + planet.getGravity(),
                "Atmosphere Pressure [Earth-norm]: " + planet.getAtmoPressure(),
                "Surface temperature [C]: " + (planet.getSurfaceTemp() - 274.0),
                "Lifeform: " + planet.getLifeType());

        TitledPane firstTitledPane = getTitledPane("Basic Facts", starFacts);

        //making the second fact pane
        ObservableList<String> atmoFacts = FXCollections.observableArrayList("Atmosphere Pressure [Earth-norm]: " + planet.getAtmoPressure(),
                                                                             "Atmosphere Composition: " + planet.getAtmosphericCompositionParsed(),
                                                                             "Hydrosphere type: " + planet.getHydrosphereDescription(),
                                                                             "Hydrosphere [%]: " + planet.getHydrosphere());

        TitledPane secondTitledPane = getTitledPane("Atmospheric Facts", atmoFacts);
//making the third fact pane
        ObservableList<String> geoFacts = FXCollections.observableArrayList("Radius [km]: " + planet.getRadius(),
                                                                            "Density [Earth-norm]: " + planet.getDensity(),
                                                                            "Core type: " + planet.getTectonicCore(),
                                                                            "Tectonic Activity: " + planet.getTectonicActivityGroup(),
                                                                            "Magnetic Field: " + planet.getMagneticField());

        TitledPane thirdTitledPane = getTitledPane("Geophysical Facts", geoFacts);

        //making the fourth fact pane
        ObservableList<String> climateFacts = FXCollections.observableArrayList("Surface temperature [C]: " + (planet.getSurfaceTemp() - 274.0),
                                                                                "Orbital Period [Earth Years]: " + planet.getOrbitalPeriod(),
                                                                                planet.isTidelocked()
                                                                                        ? "Rotational Period [Earth hours]: "
                                                                                                  + planet.getRotationalPeriod()
                                                                                        : "Planet is Tidelocked, no light changes beyound seasonals.",
                                                                                "Axial Tilt [Degrees]: " + planet.getAxialTilt(),
                                                                                "Orbital Eccentricity: " + planet.getOrbitaleccentricity());
        TitledPane fourthTitledPane = getTitledPane("Habitational Facts", climateFacts);

        //Rangeband display

        TitledPane fifthTitledPane = new TitledPane("Temperature Rangebands", getTemperatureRangeBandHelpClassTableView(planet));

        //Making the page
        Accordion furtherFacts = new Accordion();
        furtherFacts.getPanes().addAll(firstTitledPane, secondTitledPane, thirdTitledPane, fourthTitledPane, fifthTitledPane);
        infoPage.getChildren().add(furtherFacts);

        return infoPage;
    }

    private static Group getSphere(Planet planet, Image normalMap) {
        Sphere planetSphere = new Sphere(100);
        planetSphere.setTranslateX(200);
        planetSphere.setTranslateY(150);

        final PhongMaterial planetColour = new PhongMaterial();
        findObjectColour(planet, planetColour);
        planetSphere.setMaterial(planetColour);

        planetColour.setBumpMap(normalMap);
        AmbientLight light = new AmbientLight(new Color(1.0, 1.0, 1.0, 0.5));

        PointLight light2 = new PointLight(Color.WHITE);
        return new Group(planetSphere, light, light2);
    }

    private static TableView<TemperatureRangeBandHelpClass> getTemperatureRangeBandHelpClassTableView(Planet planet) {
        TableView<TemperatureRangeBandHelpClass> temperatureTable = new TableView<TemperatureRangeBandHelpClass>();
        ObservableList<TemperatureRangeBandHelpClass> temperatures = FXCollections.observableArrayList();

        TemperatureRangeBandHelpClass base = new TemperatureRangeBandHelpClass("Base temp [C]", planet.getRangeBandTemperature());
        TemperatureRangeBandHelpClass summer = new TemperatureRangeBandHelpClass("Summer increase [C]", planet.getRangeBandTempSummer());
        TemperatureRangeBandHelpClass winter = new TemperatureRangeBandHelpClass("Winter decresae [C]", planet.getRangeBandTempWinter());

        temperatures.add(base);
        temperatures.add(summer);
        temperatures.add(winter);

        temperatureTable.itemsProperty().set(temperatures);

        TableColumn<TemperatureRangeBandHelpClass, String> name = new TableColumn<>("Type");
        TableColumn<TemperatureRangeBandHelpClass, Integer> one = new TableColumn<>("Equatorial");
        TableColumn<TemperatureRangeBandHelpClass, Double> two = new TableColumn<>("5-15");
        TableColumn<TemperatureRangeBandHelpClass, Double> three = new TableColumn<>("15-25");
        TableColumn<TemperatureRangeBandHelpClass, Double> four = new TableColumn<>("25-35");
        TableColumn<TemperatureRangeBandHelpClass, Double> five = new TableColumn<>("35-45");
        TableColumn<TemperatureRangeBandHelpClass, Double> six = new TableColumn<>("45-55");
        TableColumn<TemperatureRangeBandHelpClass, Double> seven = new TableColumn<>("55-65");
        TableColumn<TemperatureRangeBandHelpClass, Double> eight = new TableColumn<>("65-75");
        TableColumn<TemperatureRangeBandHelpClass, Double> nine = new TableColumn<>("75-85");
        TableColumn<TemperatureRangeBandHelpClass, Double> ten = new TableColumn<>("Polar");

        name.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, String>("name"));
        one.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, Integer>("one"));
        two.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, Double>("two"));
        three.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, Double>("three"));
        four.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, Double>("four"));
        five.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, Double>("five"));
        six.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, Double>("six"));
        seven.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, Double>("seven"));
        eight.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, Double>("eight"));
        nine.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, Double>("nine"));
        ten.setCellValueFactory(new PropertyValueFactory<TemperatureRangeBandHelpClass, Double>("ten"));

        temperatureTable.getColumns().addAll(name, one, two, three, four, five, six, seven, eight, nine, ten);
        return temperatureTable;
    }

    private static TitledPane getTitledPane(String title, ObservableList<String> facts) {

        ListView<String> factList = getFactList(facts);
        VBox geoFactBox = new VBox();
        geoFactBox.getChildren().add(factList);
        geoFactBox.setPadding(new Insets(15, 12, 15, 12));
        return new TitledPane(title, geoFactBox);
    }

    private static ListView<String> getFactList(ObservableList<String> facts) {
        ListView<String> factList = new ListView<>();
        factList.setItems(facts);
        factList.maxWidth(50);
        factList.setPrefHeight(150);
        return factList;
    }


    private static VBox PageGenerator(Star star) {

        VBox infoPage = new VBox();

        Text titelInfo = new Text(star.getName() + " (" + star.getClassification() + ")");

        titelInfo.setFont(Font.font("Verdana", 20));

        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().add(titelInfo);
        topBox.setPadding(new Insets(15, 12, 15, 12));

        infoPage.getChildren().add(topBox);


        TextArea description = new TextArea(star.getDescription());
        description.setPrefColumnCount(60);
        description.setWrapText(true);
        //Making the picture of star
        Sphere starSphere = new Sphere(100);
        starSphere.setTranslateX(200);
        starSphere.setTranslateY(150);

        final PhongMaterial starColour = new PhongMaterial();

        findObjectColour(star, starColour);

        starSphere.setMaterial(starColour);

        Image normalMap = new Image("/normalmap.png");

        starColour.setBumpMap(normalMap);

        Color ambiColor = new Color(1.0, 1.0, 1.0, 0.1);

        AmbientLight light = new AmbientLight();
        light.setColor(ambiColor);

        PointLight light2 = new PointLight();
        light2.setColor(Color.WHITE);

        BackgroundImage myBI = getBackgroundImage("/resources/Starfield.png");

        Group displayStar = new Group(starSphere, light, light2);
        //making the factbox
        HBox test = new HBox(displayStar);
        test.setBackground(new Background(myBI));

        HBox intro = new HBox(description, test);
        intro.setPadding(new Insets(15, 12, 15, 12));
        intro.setSpacing(10);

        infoPage.getChildren().add(intro);

        TitledPane firstTitledPane = new TitledPane();
        firstTitledPane.setText("Facts");

        ListView<String> factList = new ListView<>();

        ObservableList<String> starFacts = FXCollections.observableArrayList("Lumosity [Sol-eqv]: " + star.getLumosity(),
                                                                             "Mass [Sol-eqv]: " + star.getMass(),
                                                                             "Diameter [Sol-eqv]: " + star.getAge());

        factList.setItems(starFacts);
        factList.maxWidth(50);
        factList.setPrefHeight(100);

        VBox factBox = new VBox();

        factBox.getChildren().add(factList);
        factBox.setPadding(new Insets(15, 12, 15, 12));

        firstTitledPane.setContent(factBox);

        //Making the other listnings (here the other main objects in the system)
        TitledPane secondTitledPane = new TitledPane();
        secondTitledPane.setText("System Objects");

        TableView<StellarObject> table = new TableView<StellarObject>();
        ObservableList<StellarObject> systemOrbitsObjects = FXCollections.observableArrayList();

        for (int i = 1; i < star.getOrbitalObjects().size(); i++) {
            systemOrbitsObjects.add(CentralRegistry.getFromArchive(star.getOrbitalObjects().get(i)));
        }
        table.itemsProperty().set(systemOrbitsObjects);

        TableColumn<StellarObject, Double> orbit = new TableColumn<>("Orbit Distance [Au]");
        TableColumn<StellarObject, String> name = new TableColumn<>("Object Name");
        TableColumn<StellarObject, String> type = new TableColumn<>("Object Type");
        TableColumn<StellarObject, String> life = new TableColumn<>("Native Life");

        orbit.setCellValueFactory(new PropertyValueFactory<StellarObject, Double>("orbitDistanceStar"));
        name.setCellValueFactory(new PropertyValueFactory<StellarObject, String>("name"));
        type.setCellValueFactory(new PropertyValueFactory<StellarObject, String>("classificationName"));
        life.setCellValueFactory(new PropertyValueFactory<StellarObject, String>("lifeType"));

        table.getColumns().addAll(orbit, name, type, life);

        secondTitledPane.setContent(table);

        Accordion furtherFacts = new Accordion();
        furtherFacts.getPanes().addAll(firstTitledPane, secondTitledPane);

        infoPage.getChildren().add(furtherFacts);

        return infoPage;
    }

    private static void findObjectColour(Planet planet, PhongMaterial planetColour) {

        //TODO should be more variation
        planetColour.setDiffuseColor(Color.DARKGRAY);

    }

    private static void findObjectColour(Jovian jovian, PhongMaterial jovianColour) {
        //TODO should be more variation

        jovianColour.setDiffuseColor(Color.LIGHTBLUE);

    }

    private static void findObjectColour(Star star, PhongMaterial starColour) {

        switch (star.getClassification().charAt(0)) {
            case 'Y':
                starColour.setDiffuseColor(Color.BROWN);
                break;
            case 'T':
                starColour.setDiffuseColor(Color.DARKRED);
                break;
            case 'M':
                starColour.setDiffuseColor(Color.CRIMSON);
                break;
            case 'K':
                starColour.setDiffuseColor(Color.ORANGE);
                break;
            case 'G':
                starColour.setDiffuseColor(Color.YELLOW);
                break;
            case 'F':
                starColour.setDiffuseColor(Color.LIGHTGREEN);
                break;
            case 'A':
                starColour.setDiffuseColor(Color.WHITE);
                break;
            case 'B':
                starColour.setDiffuseColor(Color.LIGHTBLUE);
                break;
            case 'O':
                starColour.setDiffuseColor(Color.BLUE);
                break;
            default:
                starColour.setDiffuseColor(Color.DARKORCHID);
                break;
        }
    }
}
