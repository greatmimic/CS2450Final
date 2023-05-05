package application;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


//@@@@TODO@@@@
//Create self scrolling news highlight bar with main, entertainment, sports, finance buttons
//create tabs for entertainment, sports, auto, finance, food, fashion		DONE
//create fake ad banner in between search bar and news grid       DONE
//create a chart simulation of billboard top 100 somewhere		
//create a single area weather forecast (one city)
//get proper icons for nav buttons
//create login interface
//edit onAction button for news grid to open actual news website links		DONE

public class Main extends Application {

	//load image
	private Image loadImage(String imagePath) {
		try {
			InputStream imageStream = getClass().getResourceAsStream(imagePath);
			if (imageStream == null) {
				throw new FileNotFoundException("Resource not found: " + imagePath);
			}
			return new Image(imageStream);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	//Get news company url from icon
	private String getNewsCompanyUrl(String newsCompanyName) {
		switch (newsCompanyName) {
		case "CNN":
			return "https://www.cnn.com";
		case "BBC":
			return "https://www.bbc.com";
		case "FOX":
			return "https://www.foxnews.com";
		case "Aol.":
			return "https://www.aol.com";
		default:
			return "";
		}
	}


	// Populate tab content
	private HBox createTabContent(String imagePath1, String title1, String imagePath2, String title2) {
		ImageView imageView1 = new ImageView(loadImage(imagePath1));
		imageView1.setFitWidth(200);
		imageView1.setFitHeight(200);

		Text titleLabel1 = new Text(title1);
		titleLabel1.setWrappingWidth(200);
		titleLabel1.setTextAlignment(TextAlignment.CENTER);

		VBox content1 = new VBox(imageView1, titleLabel1);
		content1.setSpacing(10);
		content1.setAlignment(Pos.CENTER);
		content1.setPadding(new Insets(20, 0, 0, 0));

		ImageView imageView2 = new ImageView(loadImage(imagePath2));
		imageView2.setFitWidth(200);
		imageView2.setFitHeight(200);

		Text titleLabel2 = new Text(title2);
		titleLabel2.setWrappingWidth(200);
		titleLabel2.setTextAlignment(TextAlignment.CENTER);

		VBox content2 = new VBox(imageView2, titleLabel2);
		content2.setSpacing(10);
		content2.setAlignment(Pos.CENTER);
		content2.setPadding(new Insets(20, 0, 0, 0)); 

		HBox tabContent = new HBox(content1, content2);
		tabContent.setSpacing(20);
		tabContent.setAlignment(Pos.CENTER);

		return tabContent;
	}


	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();

		// Header section with Yahoo logo and search bar
		HBox header = new HBox();
		header.setPadding(new Insets(20, 0, 0, 0));
		header.setAlignment(Pos.CENTER);
		header.setSpacing(0);

		ImageView yahooLogo = new ImageView(loadImage("/Images/yahoo_icon.png"));
		yahooLogo.setFitWidth(30);
		yahooLogo.setFitHeight(30);
		Label yahooText = new Label("Yahoo");
		yahooText.getStyleClass().add("yahoo-text");

		TextField searchBar = new TextField();
		searchBar.setPrefWidth(600);
		searchBar.setPrefHeight(30);
		searchBar.setPromptText("Search for news, stocks, and more");
		searchBar.setFocusTraversable(false); // remove default focus
		searchBar.setOnMouseClicked(event -> {
			searchBar.requestFocus();
		});


		//search button
		ImageView searchIcon = new ImageView(loadImage("/Images/search_icon.png"));
		searchIcon.setFitWidth(20);
		searchIcon.setFitHeight(20);
		Button searchButton = new Button("", searchIcon);
		searchButton.setPrefHeight(16);
		searchButton.setPrefWidth(16);
		searchButton.getStyleClass().add("search-button");


		//search box
		HBox searchBox = new HBox(searchBar, searchButton);
		searchBox.setAlignment(Pos.CENTER);
		searchBox.getStyleClass().add("search-box");
		HBox.setMargin(searchBox, new Insets(0, 0, 0, 10)); // Add left margin of 10 pixels


		// Handle mouse click events on the BorderPane to remove focus from search bar
		root.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			searchBar.getParent().requestFocus(); // Remove focus from search bar
		});


		header.getChildren().addAll(yahooLogo, yahooText, searchBox);







		//news companies
		String[][] newsCompanies = {
				{"CNN", "/Images/cnn_icon.png", "https://www.cnn.com"},
				{"BBC", "/Images/bbc_icon.png", "https://www.bbc.com"},
				{"FOX", "/Images/fox_icon.png", "https://www.foxnews.com"}, 
				{"Aol.","/Images/aol_icon.png", "https://www.aol.com"} 

		};



		//table of news grid pane
		GridPane newsGrid = new GridPane();
		newsGrid.setHgap(20);
		newsGrid.setVgap(20);
		newsGrid.setAlignment(Pos.CENTER);

		// Step 3: Iterate through the news companies and create a VBox for each
		for (int i = 0; i < newsCompanies.length; i++) {

			String currentNewsCompany = newsCompanies[i][0];
			ImageView logo = new ImageView(loadImage(newsCompanies[i][1]));
			logo.setFitWidth(50);
			logo.setFitHeight(50);

			Button logoButton = new Button("", logo);
			logoButton.getStyleClass().add("logo-button");



			VBox newsCompanyBox = new VBox(logoButton); // Use logoButton instead of logo
			newsCompanyBox.setAlignment(Pos.CENTER);

			// Add this code snippet inside the loop where you create the logoButton
			logoButton.setOnAction(event -> {
				String newsCompanyUrl = getNewsCompanyUrl(currentNewsCompany);
				System.out.println("Clicked on " + currentNewsCompany); // Use the local variable here
				getHostServices().showDocument(newsCompanyUrl);
			});

			// Step 4: Add each VBox to the GridPane
			newsGrid.add(newsCompanyBox, i % 3, i / 3); // Change '3' to the desired number of columns
		}



		// Navigation bar section with buttons
		HBox navBox = new HBox();
		navBox.setPadding(new Insets(10, 0, 10, 0));
		navBox.setAlignment(Pos.CENTER);
		navBox.setSpacing(20);

		Button mailButton = new Button("Mail");
		mailButton.getStyleClass().add("nav-button");
		Button newsButton = new Button("News");
		newsButton.getStyleClass().add("nav-button");
		Button sportsButton = new Button("Sports");
		sportsButton.getStyleClass().add("nav-button");
		Button financeButton = new Button("Finance");
		financeButton.getStyleClass().add("nav-button");
		Button entertainmentButton = new Button("Entertainment");
		entertainmentButton.getStyleClass().add("nav-button");
		Button moreButton = new Button("More");
		moreButton.getStyleClass().add("nav-button");


		navBox.getChildren().addAll(mailButton, newsButton, sportsButton, financeButton, entertainmentButton, moreButton);



		// Image banner section
		HBox imageBanner = new HBox();
		imageBanner.setPadding(new Insets(10, 0, 10, 0));
		imageBanner.setAlignment(Pos.CENTER);
		imageBanner.setSpacing(20);

		ImageView bannerImage = new ImageView(loadImage("/Images/ad_banner.png"));
		bannerImage.setFitHeight(100);
		bannerImage.setPreserveRatio(true);

		imageBanner.getChildren().add(bannerImage);
		bannerImage.setOnMouseClicked(event -> {
			System.out.println("Clicked on ad banner"); // Replace this with your desired action
		});
		imageBanner.getStyleClass().add("ad-banner");



		VBox topSection = new VBox();
		topSection.getChildren().addAll(header, navBox, imageBanner);

		root.setTop(topSection);
		root.setCenter(newsGrid);





		//tabs 

		// Create tabs
		Tab entmtTab = new Tab("Entertainment");
		Tab foodTab = new Tab("Food");
		Tab sportsTab = new Tab("Sports");
		Tab autoTab = new Tab("Auto");
		Tab financeTab = new Tab("Finance");
		Tab fashionTab = new Tab("Fashion");


		// Create TabPane and add tabs to it
		TabPane tabPane = new TabPane();
		tabPane.getTabs().addAll(entmtTab, foodTab, sportsTab, autoTab, financeTab, fashionTab);
		tabPane.setTabMinWidth(80); // Set the minimum width of each tab
		tabPane.setTabMaxWidth(80); // Set the maximum width of each tab
		// Set closable property to false for all tabs
		for (Tab tab : tabPane.getTabs()) {
			tab.setClosable(false);
		}

		HBox tabPaneBox = new HBox(tabPane);
		tabPaneBox.setMaxWidth(800);
		tabPaneBox.setAlignment(Pos.CENTER);

		// Tab content
		entmtTab.setContent(createTabContent("/Images/entmt_1.png", "Writer Strike 2023 Explained", "/Images/entmt_2.png", "Illegal Twitter Upload of 'Super Mario Bros.'"));
		foodTab.setContent(createTabContent("/Images/food_1.png", "General Mills Recall on Flour Due To Salmonella", "/Images/food_2.png", "How a Burger Ends Up On Your Plate"));
		sportsTab.setContent(createTabContent("/Images/sports_1.png", "3 Keys To The Series", "/Images/sports_2.png", "PSG Suspends Messi"));
		autoTab.setContent(createTabContent("/Images/auto_1.png", "List of Cars Under $20K", "/Images/auto_2.png", "Mustang Plows Into U-HAUL Trailer"));
		financeTab.setContent(createTabContent("/Images/finance_1.png", "Bankrupt Bed Bath & Beyond Seeks Millions From Ocean Carriers", "/Images/finance_2.png", "Starbucks Beats Earnings and Sales. Why the Stock is Down"));
		fashionTab.setContent(createTabContent("/Images/fashion_1.png", "What's Trending 2023", "/Images/fashion_2.png", "9 Fresh Ways to Wear Jean Shorts for Summer 2023"));





		// Add tabPane and newsGrid to the center of the BorderPane
		VBox centerBox = new VBox(newsGrid, tabPaneBox);
		centerBox.setSpacing(40);
		centerBox.setAlignment(Pos.CENTER);
		root.setCenter(centerBox);





		Scene scene = new Scene(root, 1600, 1000);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		primaryStage.setTitle("Yahoo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
