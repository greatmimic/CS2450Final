package application;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.*;






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
		case "NBC":
			return "https://www.nbcnews.com";
		case "CBS":
			return "https://www.cbsnews.com";
		default:
			return "";
		}
	}

	private GridPane newsGrid;
	private TabPane tabPane;
	private VBox signInBox;
	private VBox weatherVBox;
	private VBox trendingBox;
	private WebEngine webEngine;
	private WebView webView;
	private Button closeButton;
	private StackPane webViewLayout;
	private ImageView seeMoreIcon;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	//trending article section
	public static String getTrendingSearchUrl(String trendingSearch) {
	    return "https://www.search.yahoo.com/search?q=" + trendingSearch;
	    }

	private VBox createTrendingBox() {
		trendingBox = new VBox();
		trendingBox.setSpacing(10);
		trendingBox.setPadding(new Insets(10));
		trendingBox.setAlignment(Pos.TOP_CENTER);
		trendingBox.setMinWidth(400);
		trendingBox.getStyleClass().add("trending-bordered-box");

		// Add "Trending Now" label
		Label trendingLabel = new Label("Trending Now");
		trendingLabel.setMinWidth(380);
		trendingLabel.getStyleClass().add("trending-label");
		trendingLabel.setAlignment(Pos.CENTER);
		trendingBox.getChildren().add(trendingLabel);

		// Add black outline to the VBox
		trendingBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
				null, new BorderWidths(1))));

		// Populate with trending Searches
		String[] trendingSearches = {
				"1. The Voice",
				"2. New Mexico Shooting",
				"3. Ja Morant",
				"4. Shark Attacks Kayak",
				"5. Jamie Foxx"
		};

		webView = new WebView();
		webEngine = webView.getEngine();
		
		for (String searchTitle : trendingSearches) {
			Text searchText = new Text(searchTitle);
			searchText.setWrappingWidth(200);
			searchText.setTextAlignment(TextAlignment.LEFT);
			searchText.getStyleClass().add("trending-searches");
			
			searchText.setOnMouseClicked(event -> {
				String searchUrl = getTrendingSearchUrl(searchTitle);
				System.out.println("Clicked on " + searchTitle);
				webEngine.load(searchUrl);
	            webView.setVisible(true);
	            webView.toFront();
	            setComponentsVisibility(false);
	            closeButton.setVisible(true);
	            webViewLayout.setVisible(true);
	            webViewLayout.setManaged(true);
			});

			searchText.setOnMouseEntered(e -> {
				searchText.setFill(Color.PURPLE);
				searchText.setCursor(Cursor.HAND);
			});

			searchText.setOnMouseExited(e -> {
				searchText.setFill(Color.BLACK);
			});

			trendingBox.getChildren().add(searchText);
		}

		return trendingBox;
	}

	@Override
	public void start(Stage primaryStage) {
		VBox trendingBox = createTrendingBox();

		BorderPane mainLayout = new BorderPane();
		GridPane root = new GridPane();

		// Header section with Yahoo logo and search bar
		HBox header = new HBox();
		header.setPadding(new Insets(20, 0, 0, 0));
		header.setAlignment(Pos.CENTER);
		header.setSpacing(0);
		
		
		
		// Set up see more icon imageView to use later
		seeMoreIcon = new ImageView(loadImage("/Images/see_more_icon.png"));
		
		
		
		
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////SEARCH BAR AND LOGO////////////////////////////////////////////////////////////////////////////////
		ImageView yahooLogo = new ImageView(loadImage("/Images/yahoo_logo.png"));
		yahooLogo.setFitWidth(110);
		yahooLogo.setFitHeight(50);


		TextField searchBar = new TextField();
		searchBar.setPrefWidth(600);
		searchBar.setPrefHeight(30);
		searchBar.setPromptText("Search for news, stocks, and more");
		searchBar.setFocusTraversable(false); // remove default focus
		searchBar.setDisable(false);
		searchBar.setOnMouseClicked(event -> {
			searchBar.requestFocus();
		});



		//search button
		ImageView searchIcon = new ImageView(loadImage("/Images/search_icon.png"));
		searchIcon.setFitWidth(20);
		searchIcon.setFitHeight(20);
		Button searchButton = new Button("", searchIcon);
		searchButton.setPrefHeight(25);
		searchButton.setPrefWidth(25);
		searchButton.getStyleClass().add("search-button");


		//search box
		HBox searchBox = new HBox(searchBar, searchButton);
		searchBox.setAlignment(Pos.CENTER);
		searchBox.getStyleClass().add("search-box");
		HBox.setMargin(searchBox, new Insets(0, 0, 0, 10)); // Add left margin of 10 pixels


		// Handle mouse click events on the BorderPane to remove focus from search bar
		//		root.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
		//			searchBar.getParent().requestFocus(); // Remove focus from search bar
		//		});


		header.getChildren().addAll(yahooLogo,  searchBox);


		///////////////////////////////////////////////////////////////////////////////NEWS GRID//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		webView = new WebView();
		webEngine = webView.getEngine();
		webViewLayout = new StackPane();
		webViewLayout.getChildren().add(webView);

		ImageView closeIcon = new ImageView(loadImage("/Images/close_icon.png"));
		closeButton = new Button("", closeIcon);
		webViewLayout.getChildren().add(closeButton);
		closeButton.setVisible(false);
		closeButton.setTranslateX(1015);
		closeButton.setTranslateY(-360);
		closeButton.getStyleClass().add("nav-button");

		webView.setOnScroll(event -> {
		    double deltaY = event.getDeltaY();
		    Object scrollYObj = webView.getEngine().executeScript("window.pageYOffset");
		    if (scrollYObj instanceof Number) {
		        double scrollY = ((Number) scrollYObj).doubleValue();
		        scrollY -= deltaY * 10; // Adjust scroll speed by changing the multiplier (40 in this example)
		        webView.getEngine().executeScript("window.scrollTo(0, " + scrollY + ")");
		    }
		    event.consume();
		});








		// Initially webView is not visible
		root.getChildren().add(webViewLayout);
		webView.setVisible(false);
		webView.setPrefSize(1280, 720);
		webView.setTranslateX(500);
		webView.setTranslateY(-30);

		//news companies
		String[][] newsCompanies = {
				{"CNN", "/Images/cnn_icon.png", "https://www.cnn.com"},
				{"BBC", "/Images/bbc_icon.png", "https://www.bbc.com"},
				{"FOX", "/Images/fox_icon.png", "https://www.foxnews.com"}, 
				{"Aol.","/Images/aol_icon.png", "https://www.aol.com"}, 
				{"NBC", "/Images/nbc_icon.png", "https://www.nbcnews.com"},
				{"CBS", "/Images/cbs_icon.png", "https://cbsnews.com"},

		};

		//table of news grid pane
		newsGrid = new GridPane();
		newsGrid.setHgap(20);
		newsGrid.setVgap(20);
		newsGrid.setAlignment(Pos.CENTER);



		// Iterate through the news companies and create a VBox for each
		for (int i = 0; i < newsCompanies.length; i++) {

			String currentNewsCompany = newsCompanies[i][0];
			ImageView logo = new ImageView(loadImage(newsCompanies[i][1]));
			logo.setFitWidth(80);
			logo.setFitHeight(80);

			Button logoButton = new Button("", logo);
			logoButton.getStyleClass().add("logo-button");



			VBox newsCompanyBox = new VBox(logoButton); // Use logoButton instead of logo
			newsCompanyBox.setAlignment(Pos.CENTER);


			logoButton.setOnAction(event -> {
				String newsCompanyUrl = getNewsCompanyUrl(currentNewsCompany);
				webEngine.load(newsCompanyUrl); // Load the web page in the WebView
				webView.setVisible(true);
				webView.toFront();
				setComponentsVisibility(false);
				closeButton.setVisible(true);
				webViewLayout.setVisible(true);
				webViewLayout.setManaged(true);

			});

			// Add each VBox to the GridPane
			newsGrid.add(newsCompanyBox, i % 3, i / 3); 
		}

		///////////////////////////////////////////////////CLOSE BUTTON FOR WEBVIEW//////////////////////////////////////////////////////////




		// Set visibility back to true when close button is clicked
		closeButton.setOnMouseClicked(e->{
			// Hide webView 
			webViewLayout.setVisible(false);
			webViewLayout.setManaged(false);
			// Show other controls
			setComponentsVisibility(true);

		});


		
		
		


		///////////////////////////////////////////////////////////////////////////////LOGIN INTERFACE//////////////////////////////

		//Login Interface
		signInBox = new VBox();
		signInBox.setAlignment(Pos.CENTER);

		Button signInButton = new Button("Click To Sign-In");
		signInButton.setPrefHeight(40);
		signInButton.setPrefWidth(150);

		signInButton.setOnAction(e->{
			showLoginInterface(signInBox);
		});


		signInBox.getChildren().add(signInButton);

		///////////////////////////////////////////////////////////////////////////////MAKE HOMEPAGE LABEL/////////////////////////////////////////////////////////////////////////		

		// Create the "Make Homepage" label
		Button makeHomepage = new Button("Make Homepage");
		makeHomepage.getStyleClass().add("make-homepage");

		// Create a layout container to hold the label
		StackPane makeHomepagePane = new StackPane(makeHomepage);
		makeHomepagePane.setAlignment(Pos.TOP_RIGHT);
		makeHomepagePane.setPadding(new Insets(10));
		makeHomepagePane.setTranslateY(-220);
		makeHomepagePane.setTranslateX(-400);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

		
		// Weather
		HBox weatherHBox = new HBox();
		weatherVBox = new VBox();

		Label wLabel1 = new Label("Weather");
		wLabel1.getStyleClass().add("weather");
		wLabel1.setMinWidth(380);
		
		
		
		Label wLabel2 = new Label(" Mon.");
		Label wWeather1 = new Label(" 81F  55F ");
		wLabel2.getStyleClass().add("weatherLabel");
	
		
		Label wLabel3 = new Label(" Tue.");
		Label wWeather2 = new Label(" 73F  56F ");
		wLabel3.getStyleClass().add("weatherLabel");
		
	
		Label wLabel4 = new Label(" Wed. ");
		Label wWeather3 = new Label(" 67F   52F ");
		wLabel4.getStyleClass().add("weatherLabel");
		
		
		Label wLabel5 = new Label(" Thur. ");
		Label wWeather4 = new Label(" 54F  30F ");
		wLabel5.getStyleClass().add("weatherLabel");
		
		Button wMoreButton = new Button("See More");
		wMoreButton.setGraphic(seeMoreIcon);
		wMoreButton.getStyleClass().add("nav-button");
		
		wMoreButton.setOnAction(event -> {
		    String newUrl = "https://www.yahoo.com/news/weather/forecast/12796088"; 
		    webEngine.load(newUrl); 
		    webView.setVisible(true);
		    webView.toFront();
		    setComponentsVisibility(false);
		    closeButton.setVisible(true);
		    webViewLayout.setVisible(true);
		    webViewLayout.setManaged(true);
		});
		
		
		
		
		

		ImageView sunny = new ImageView(loadImage("/Images/sunny_icon.png"));
		ImageView rainy = new ImageView(loadImage("/Images/rainy_icon.png"));
		ImageView thunder = new ImageView(loadImage("/Images/thunder_icon.png"));
		ImageView snowy = new ImageView(loadImage("/Images/snowy_icon.png"));

		VBox mondayVbox = new VBox(10, wLabel2, sunny, wWeather1);
		mondayVbox.getStyleClass().add("monday");
		mondayVbox.setAlignment(Pos.CENTER);
		mondayVbox.setMinWidth(87);
		mondayVbox.setMaxWidth(87);
		
		VBox tuesdayVbox = new VBox(10, wLabel3, rainy, wWeather2);
		tuesdayVbox.getStyleClass().add("monday");
		tuesdayVbox.setAlignment(Pos.CENTER);
		tuesdayVbox.setMinWidth(87);
		tuesdayVbox.setMaxWidth(87);
		
		VBox wednesdayVbox = new VBox(10, wLabel4, thunder, wWeather3);
		wednesdayVbox.getStyleClass().add("monday");
		wednesdayVbox.setAlignment(Pos.CENTER);
		wednesdayVbox.setMinWidth(87);
		wednesdayVbox.setMaxWidth(87);
		
		VBox thursdayVbox = new VBox(10, wLabel5, snowy, wWeather4);
		thursdayVbox.getStyleClass().add("monday");
		thursdayVbox.setAlignment(Pos.CENTER);
		thursdayVbox.setMinWidth(87);
		thursdayVbox.setMaxWidth(87);

		weatherHBox.getChildren().addAll(mondayVbox, tuesdayVbox, wednesdayVbox, thursdayVbox);
		weatherVBox.getChildren().addAll(wLabel1, weatherHBox, wMoreButton);
		weatherVBox.setSpacing(10);
		weatherHBox.setSpacing(10);
		wLabel1.setAlignment(Pos.CENTER);
		weatherVBox.setAlignment(Pos.CENTER);
		weatherVBox.setMinWidth(400);
		weatherVBox.getStyleClass().add("weather-bordered-box");
		weatherHBox.setAlignment(Pos.CENTER);




		///////////////////////////////////////////////////////////////////////////////NAVIGATION BUTTONS///////////////////////////////////////////////////////////////////////////

		// Navigation bar section with buttons
		HBox navBox = new HBox();
		navBox.setPadding(new Insets(10, 0, 10, 0));
		navBox.setAlignment(Pos.CENTER);
		navBox.setSpacing(20);
		ImageView homeIcon = new ImageView(loadImage("/Images/home_icon.png"));
		homeIcon.setFitWidth(20);
		homeIcon.setFitHeight(20);


		Button homeButton = new Button("Home", homeIcon);
		homeButton.getStyleClass().add("nav-button");
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
		MenuButton moreButton = new MenuButton("More...");
		moreButton.getStyleClass().add("menu-button");
		createDropdownMenu(moreButton);



		navBox.getChildren().addAll(homeButton, mailButton, newsButton, sportsButton, financeButton, entertainmentButton, moreButton);

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		///////////////////////////////////////////////////////////////////////////////AD BANNER///////////////////////////////////////////////////////////////////////////////		


		// Image banner section
		HBox imageBanner = new HBox();
		imageBanner.setPadding(new Insets(10, 0, 10, 0));
		imageBanner.setAlignment(Pos.CENTER);
		imageBanner.setSpacing(20);

		ImageView bannerImage = new ImageView(loadImage("/Images/ad_banner.png"));
		bannerImage.setFitHeight(100);
		bannerImage.setPreserveRatio(true);
		bannerImage.getStyleClass().add("ad-banner");

		imageBanner.getChildren().add(bannerImage);



		VBox topSection = new VBox();
		topSection.getChildren().addAll(header, navBox, imageBanner, makeHomepagePane);

		mainLayout.setTop(topSection);
		mainLayout.setCenter(root);


		///////////////////////////////////////////////////////////////////////////////TABS//////////////////////////////////////////////////////////////////////////////////////

		// Create tabs
		Tab entmtTab = new Tab("Entertainment");
		Tab foodTab = new Tab("Health");
		Tab sportsTab = new Tab("Sports");
		Tab autoTab = new Tab("Auto");
		Tab financeTab = new Tab("Finance");
		Tab fashionTab = new Tab("Shopping");


		// Create TabPane and add tabs to it
		tabPane = new TabPane();
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
		entmtTab.setContent(createTabContent("/Images/entmt_1.png", "Writer Strike 2023 Explained", "/Images/entmt_2.png", "Illegal Twitter Upload of 'Super Mario Bros.'", "https://www.yahoo.com/entertainment/"));
		foodTab.setContent(createTabContent("/Images/food_1.png", "General Mills Recall on Flour Due To Salmonella", "/Images/food_2.png", "How a Burger Ends Up On Your Plate", "https://www.yahoo.com/lifestyle/tagged/health"));
		sportsTab.setContent(createTabContent("/Images/sports_1.png", "3 Keys To The Series", "/Images/sports_2.png", "PSG Suspends Messi", "https://sports.yahoo.com/"));
		autoTab.setContent(createTabContent("/Images/auto_1.png", "List of Cars Under $20K", "/Images/auto_2.png", "Mustang Plows Into U-HAUL Trailer", "https://autos.yahoo.com/"));
		financeTab.setContent(createTabContent("/Images/finance_1.png", "Bankrupt Bed Bath & Beyond Seeks Millions From Ocean Carriers", "/Images/finance_2.png", "Starbucks Beats Earnings and Sales. Why the Stock is Down", "https://finance.yahoo.com/"));
		fashionTab.setContent(createTabContent("/Images/fashion_1.png", "What's Trending 2023", "/Images/fashion_2.png", "9 Fresh Ways to Wear Jean Shorts for Summer 2023", "https://shopping.yahoo.com/"));

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


		// Add tabPane and newsGrid to the center of the BorderPane
		VBox centerBox = new VBox(newsGrid, tabPaneBox);
		centerBox.setSpacing(40);
		centerBox.setAlignment(Pos.CENTER);
		centerBox.setTranslateY(0);
		centerBox.setTranslateX(100);

		VBox rightContentBox = new VBox(signInBox, trendingBox, weatherVBox);
		rightContentBox.setSpacing(70);
		rightContentBox.setAlignment(Pos.CENTER);

		StackPane mainCenter = new StackPane(centerBox, rightContentBox);
		mainCenter.setAlignment(Pos.CENTER);

		//root.setCenter(mainCenter);

		//root.setTranslateX(400);


		// Set column constraints for the GridPane to distribute space evenly
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(50);

		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(50);

		root.getColumnConstraints().addAll(col1, col2);

		// Add mainCenter to the first column of the GridPane
		GridPane.setConstraints(mainCenter, 0, 0);

		// Add signInArea to the second column of the GridPane
		GridPane.setConstraints(rightContentBox, 1, 0);

		root.getChildren().addAll(mainCenter, rightContentBox);




		Scene scene = new Scene(mainLayout, 1920, 1080);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		primaryStage.setTitle("Yahoo");
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}








	public static void main(String[] args) {
		launch(args);
	}

	private void showLoginInterface(VBox signInBox) {
		// Create login components
		Label usernameLabel = new Label("Username:");
		TextField usernameField = new TextField();
		Label passwordLabel = new Label("Password:");
		PasswordField passwordField = new PasswordField();
		Button loginButton = new Button("Login");
		Button foldButton = new Button("Fold");

		//textfield sizes
		usernameField.setMaxWidth(200);
		passwordField.setMaxWidth(200);
		
		Label errorMessageLabel = new Label();
		errorMessageLabel.getStyleClass().add("error-label");
		
		// Handle login button click
		loginButton.setOnAction(e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();

			// Perform login validation here

			// Add pop up label to show login successful or unsuccessful msg
			// also do not show sign in button again. instead show Hello, User00
			if (isValidLogin(username, password)) {
				// Create welcome message label
				Label welcomeLabel = new Label("Welcome, " + username + "!");
				welcomeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

				// Create "My Account" button
				Button myAccountButton = new Button("My Account");
				myAccountButton.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

				// Add action for "My Account" hyperlink
				myAccountButton.setOnAction(event -> {
					// Perform actions when "My Account" is clicked
					System.out.println("My Account clicked");

				});

				Button logOutButton = new Button("Log Out");

				logOutButton.setOnAction(event2 -> {
					clearLoginInterface(signInBox);
				});

				// Create layout for the welcome message and "My Account" hyperlink
				VBox welcomeLayout = new VBox(10);
				welcomeLayout.setAlignment(Pos.CENTER);
				welcomeLayout.setPadding(new Insets(20));
				welcomeLayout.getChildren().addAll(welcomeLabel, myAccountButton, logOutButton);

				// Replace the content of signInBox with the welcome layout
				signInBox.getChildren().clear();
				signInBox.getChildren().add(welcomeLayout);
			} else {
				
				// Display error message
				errorMessageLabel.setText("Invalid username or password. Please try again.");
				System.out.println("Invalid username or password");
				 
			}
		});

		// Handle fold button click
		foldButton.setOnAction(e -> {
			// Clear the login interface and restore the "Sign In" button
			clearLoginInterface(signInBox);
		});

		// Create layout for the login interface
		VBox loginLayout = new VBox(10);
		loginLayout.setAlignment(Pos.CENTER);
		loginLayout.setPadding(new Insets(20));
		loginLayout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, errorMessageLabel, loginButton, foldButton);

		// Replace the content of signInBox with the login layout
		signInBox.getChildren().clear();
		signInBox.getChildren().add(loginLayout);
		signInBox.setMinHeight(100); 
		signInBox.setMaxHeight(100); 
	}
	private boolean isValidLogin(String username, String password) {
		// Return true if the login is valid, false otherwise
		return username.equals("admin") && password.equals("password");
	}
	private void clearLoginInterface(VBox signInBox) {
		signInBox.getChildren().clear();
		Button signInButton = new Button("Click To Sign-In");
		signInButton.setPrefHeight(40);
		signInButton.setPrefWidth(150);
		signInButton.setOnAction(e -> {
			showLoginInterface(signInBox);
		});
		signInBox.getChildren().add(signInButton);
	}

	private void createDropdownMenu(MenuButton moreButton) {
		MenuItem adItem = new MenuItem("Advertising");
		MenuItem helpItem = new MenuItem("Help");
		MenuItem shopItem = new MenuItem("Shopping");
		MenuItem techItem = new MenuItem("Tech");



		moreButton.getItems().addAll(adItem, helpItem, shopItem, techItem);
	}
	private void setComponentsVisibility(boolean visibility) {
		newsGrid.setVisible(visibility);
		tabPane.setVisible(visibility);
		//signInButton.setVisible(visibility);
		signInBox.setVisible(visibility);
		signInBox.setManaged(visibility);
		weatherVBox.setVisible(visibility);
		weatherVBox.setManaged(visibility);
		trendingBox.setVisible(visibility);
		trendingBox.setManaged(visibility);
	}
	
	private HBox createTabContent(String imagePath1, String title1, String imagePath2, String title2, String moreUrl) {
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
	    
	    
	    Button seeMoreButton = new Button("See More");
	    
	    
	    
	    seeMoreButton.getStyleClass().add("see-more-button");
	    seeMoreButton.setOnAction(event -> {
	        webEngine.load(moreUrl); // Load the web page in the WebView
	        webView.setVisible(true);
	        webView.toFront();
	        setComponentsVisibility(false);
	        closeButton.setVisible(true);
	        webViewLayout.setVisible(true);
	        webViewLayout.setManaged(true);
	    });

	    HBox tabContent = new HBox(content1, content2, seeMoreButton);
	    tabContent.setSpacing(20);
	    tabContent.setAlignment(Pos.CENTER);

	    return tabContent;
	}



}