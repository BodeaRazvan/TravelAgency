package com.example.travelagency.controller;

import com.example.travelagency.Main;
import com.example.travelagency.entity.Destination;
import com.example.travelagency.entity.Package;
import com.example.travelagency.entity.User;
import com.example.travelagency.repository.DestinationRepository;
import com.example.travelagency.repository.PackageRepository;
import com.example.travelagency.repository.UserRepository;
import com.example.travelagency.service.DestinationService;
import com.example.travelagency.service.PackageService;
import com.example.travelagency.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    PackageService packageService = new PackageService();
    PackageRepository packageRepository = new PackageRepository(packageService);

    UserService userService = new UserService();
    UserRepository userRepository = new UserRepository(userService);

    DestinationService destinationService = new DestinationService();
    DestinationRepository destinationRepository = new DestinationRepository(destinationService);

    @FXML private ListView<String> listViewUser;

    @FXML private TableView<Package> userTableView;
    @FXML private TableColumn<Package, String> name;
    @FXML private TableColumn<Package, String> period;
    @FXML private TableColumn<Package, Integer> price;
    @FXML private TableColumn<Package, String> status;
    @FXML private TableColumn<Package, String> extraDetails;
    @FXML private TableColumn<Package, Integer> noOfPeople;

    @FXML private TextField destinationTextFieldError;
    @FXML private TextField packageTextFieldError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // System.out.println(LoginController.getCurrUser().getUserName());
        initializeHeaders();
        refreshLists();
    }
    private void initializeHeaders(){
        this.name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        this.period.setCellValueFactory(new PropertyValueFactory<>("Period"));
        this.price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        this.status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        this.extraDetails.setCellValueFactory(new PropertyValueFactory<>("extraDetails"));
        this.noOfPeople.setCellValueFactory(new PropertyValueFactory<>("noOfPeople"));
    }

    void refreshLists(){
        listViewUser.getItems().clear();
        userTableView.getItems().clear();
        List<Destination> destinations = destinationRepository.getAllDestinations();
        ObservableList<String> destinationsList = FXCollections.observableArrayList();
        for(Destination destination: destinations){
            destinationsList.add(destination.getCountry());
        }
        listViewUser.getItems().setAll(destinationsList);
    }


    @FXML
    public void logOut() throws IOException {
        Main.setRoot("loginPage");
    }

    @FXML
    public void refresh() throws IOException {
        try{
            Main.setRoot("userPage");
            refreshLists();
        }
        catch (Exception ignored){}
    }

    private void clear(){
        destinationTextFieldError.clear();
        packageTextFieldError.clear();
    }

    public Destination getSelectedItem(){
        String dest = listViewUser.getSelectionModel().getSelectedItem();
        return destinationRepository.getDestinationByCountry(dest);
    }


    @FXML
    public void viewPackages() throws IOException {
        clear();
        try {
            userTableView.getItems().clear();
            initializeHeaders();
            Destination currentDestination = getSelectedItem();
            userTableView.getItems().addAll(currentDestination.getPackageList());
        }catch (Exception e){
            destinationTextFieldError.setText("Select a destination first");
        }
    }

    @FXML
    public void viewAllPackages(){
        clear();
        try {
            userTableView.getItems().clear();
            List<Package> packages = packageRepository.getAllNotBookedPackages();
            userTableView.getItems().addAll(packages);
        }catch(Exception e){
            packageTextFieldError.setText("Could not load packages");
        }
    }

    public Package getSelectedPackage(){
        return userTableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void bookVacation() throws IOException {
        User user = LoginController.getCurrUser();
        Package pkg = getSelectedPackage();
        if(pkg == null){
            packageTextFieldError.setText("Select a package first");
            return;
        }
        if(pkg.getStatus().equals("BOOKED")){
            packageTextFieldError.setText("No available spots for this vacation");
        }
        if(pkg.getStatus().equals("NOT_BOOKED")){
            pkg.setStatus("IN_PROGRESS");
        }
        pkg.setNoOfPeople(pkg.getNoOfPeople()-1);
        if(pkg.getNoOfPeople() == 0){
            pkg.setStatus("BOOKED");
        }
        packageRepository.modifyPackage(pkg);
        List<Package> packages = user.getPackages();
        packages.add(pkg);
        user.setPackages(packages);
        userRepository.modifyUser(user);
        refresh();
        packageTextFieldError.setText("Vacation booked");
    }

    @FXML
    public void seeBookedVacations(){
        clear();
        User user = LoginController.getCurrUser();
        List<Package> packages = user.getPackages();
        try{
            userTableView.getItems().clear();
            userTableView.getItems().addAll(packages);
        }catch (Exception e){
            packageTextFieldError.setText("could not display your packages");
        }
    }

    @FXML
    public void removeBooking() throws IOException {
        clear();
        User user = LoginController.getCurrUser();
        Package pkg = getSelectedPackage();
        if(pkg == null){
            packageTextFieldError.setText("Please select a package");
            return;
        }
        user.getPackages().remove(pkg);
        pkg.setNoOfPeople(pkg.getNoOfPeople()+1);
        if(pkg.getStatus().equals("BOOKED")){
            pkg.setStatus("IN_PROGRESS");
        }
        packageRepository.modifyPackage(pkg);
        userRepository.modifyUser(user);
        refresh();
    }
}
