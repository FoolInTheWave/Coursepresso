package com.coursepresso.project.controller;

import com.coursepresso.project.Main;
import com.coursepresso.project.entity.*;
import com.coursepresso.project.helper.StringHelper;
import com.coursepresso.project.repository.*;
import com.coursepresso.project.service.ExportService;
import com.coursepresso.project.service.ImportService;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.inject.Inject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Caleb Miller
 */
public class ImportDataController implements Initializable {

  @FXML
  private Node root;
  @FXML
  private ComboBox<Term> termCombo;
  @FXML
  private ComboBox<String> tableCombo;
  @FXML
  private TextArea previewArea;
  @FXML
  private Button backButton;
  @FXML
  private Button chooseFileButton;
  @FXML
  private Button importButton;

  @Inject
  private MainController mainController;
  @Inject
  private ExportService exportService;
  @Inject
  private ImportService importService;
  @Inject
  private DepartmentRepository departmentRepository;
  @Inject
  private RoomRepository roomRepository;
  @Inject
  private TermRepository termRepository;

  File file;

  private static final Logger log = LoggerFactory.getLogger(
      ImportDataController.class
  );

  public Node getView() {
    return root;
  }

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  void backButtonClick(ActionEvent event) {
    mainController.showMenu();
  }

  @FXML
  void getImportData(ActionEvent event) {

  }

  @FXML
  void chooseFileButtonClick(ActionEvent event) {
    final FileChooser fileChooser = new FileChooser();
    Stage stage = (Stage) Main.getScene().getWindow();

    // Set extension filters
    ArrayList<FileChooser.ExtensionFilter> extensions = new ArrayList<>();
    extensions.add(new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));
    extensions.add(new FileChooser.ExtensionFilter("Text (*.txt)", "*.txt"));
    fileChooser.getExtensionFilters().addAll(extensions);

    File chosenFile = fileChooser.showOpenDialog(stage);
    if (chosenFile != null) {
      file = chosenFile;

      try {
        previewArea.setText(
            Joiner.on('\n').join(
                Files.readAllLines(
                    Paths.get(file.getAbsolutePath())
                )
            )
        );
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      }
    }
  }

  @FXML
  void importButtonClick(ActionEvent event) {
    String table = (tableCombo.getValue() != null) ? tableCombo.getValue() : "";
    Term term = termCombo.getValue();

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Data Import");
    alert.setHeaderText(null);

    switch (table) {
      case "Appliances":
        importService.importAppliances(readApplianceFile());
        alert.setContentText("Appliances have been imported successfully!");
        break;
      case "Authorities":
        importService.importAuthorities(readAuthorityFile());
        alert.setContentText("Authorities have been imported successfully!");
        break;
      case "Course Prerequisites":
        importService.importCoursePrerequisites(readCoursePrerequisiteFile());
        alert.setContentText("Course prerequisites have been imported successfully!");
        break;
      case "Course Sections":
        if (!term.getTerm().equals("")) {
          importService.importCourseSections(readCourseSectionFile(term));
          alert.setContentText("Course Sections have been imported successfully!");
        }
        break;
      case "Courses":
        importService.importCourses(readCourseFile());
        alert.setContentText("Courses have been imported successfully!");
        break;
      case "Departments":
        departmentRepository.save(readDepartmentFile());
        alert.setContentText("Departments have been imported successfully!");
        break;
      case "Meeting Days":
        if (!term.getTerm().equals("")) {
          importService.importMeetingDays(readMeetingDayFile(term));
          alert.setContentText("Meeting days have been imported successfully!");
        }
        break;
      case "Professors":
        importService.importProfessors(readProfessorFile());
        alert.setContentText("Professors have been imported successfully!");
        break;
      case "Rooms":
        roomRepository.save(readRoomFile());
        alert.setContentText("Rooms have been imported successfully!");
        break;
      case "Terms":
        termRepository.save(readTermFile());
        alert.setContentText("Terms have been imported successfully!");
        break;
      case "Users":
        importService.importUsers(readUserFile());
        alert.setContentText("Users have been imported successfully!");
        break;
      default:
        alert.setContentText("No data was imported!");
        break;
    }

    alert.showAndWait();
  }

  public void buildView() {
    // Build rooms combo box
    ObservableList<Term> terms = FXCollections.observableArrayList(
        Lists.newArrayList(termRepository.findAll())
    );
    terms.add(0, new Term(""));
    termCombo.setItems(terms);

    // Build table names combo box
    ArrayList<String> tableNames = new ArrayList<>();
    for (String name : exportService.getTableNames()) {
      name = name.replace("_", " ");
      name = StringHelper.toTitleCase(name);
      tableNames.add(name);
    }
    ObservableList<String> tables = FXCollections.observableArrayList(
        tableNames
    );
    tableCombo.setItems(tables);

    termCombo.getSelectionModel().clearSelection();
    tableCombo.getSelectionModel().clearSelection();
    previewArea.clear();
  }

  private List<Appliance> readApplianceFile() {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Appliance> appliances = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      try (CSVParser parser = new CSVParser(new FileReader(file), format)) {

        for (CSVRecord record : parser) {
          Appliance appliance = new Appliance();
          appliance.setType(record.get("type"));
          appliance.setRoom(new Room(record.get("room_number")));

          appliances.add(appliance);
        }

        // Close the parser
        parser.close();
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      }
    }
    return appliances;
  }

  private List<Authority> readAuthorityFile() {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Authority> authorities = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      try (CSVParser parser = new CSVParser(new FileReader(file), format)) {

        for (CSVRecord record : parser) {
          Authority authority = new Authority();
          authority.setUser(new User(record.get("username")));
          authority.setAuthority(record.get("authority"));

          authorities.add(authority);
        }

        // Close the parser
        parser.close();
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      }
    }
    return authorities;
  }

  private List<Course> readCourseFile() {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Course> courses = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      try (CSVParser parser = new CSVParser(new FileReader(file), format)) {

        for (CSVRecord record : parser) {
          Course course = new Course();
          course.setCourseNumber(record.get("course_number"));
          course.setDepartment(new Department(record.get("department")));
          course.setTitle(record.get("title"));
          course.setCredits(Integer.parseInt(record.get("credits")));
          course.setDescription(record.get("description"));
          course.setAcademicLevel(record.get("academic_level"));

          courses.add(course);
        }

        // Close the parser
        parser.close();
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      }
    }
    return courses;
  }

  private List<CoursePrerequisite> readCoursePrerequisiteFile() {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<CoursePrerequisite> prerequisites = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      try (CSVParser parser = new CSVParser(new FileReader(file), format)) {

        for (CSVRecord record : parser) {
          CoursePrerequisite prerequisite = new CoursePrerequisite();
          prerequisite.setCourse(new Course(record.get("course_number")));
          prerequisite.setPrerequisite(new Course(record.get("prerequisite")));

          prerequisites.add(prerequisite);
        }

        // Close the parser
        parser.close();
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      }
    }
    return prerequisites;
  }

  private List<CourseSection> readCourseSectionFile(Term term) {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<CourseSection> sections = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Initialize the CSVParser object
    if (file != null) {
      try (CSVParser parser = new CSVParser(new FileReader(file), format)) {

        for (CSVRecord record : parser) {
          CourseSection section = new CourseSection();
          section.setCourse(new Course(record.get("course_number")));
          section.setSectionNumber(Integer.parseInt(record.get(
              "section_number"
          )));
          section.setAvailable(Boolean.parseBoolean(record.get("available")));
          section.setCapacity(Integer.parseInt(record.get("capacity")));
          section.setSeatsAvailable(Integer.parseInt(record.get(
              "seats_available"
          )));
          section.setStatus(record.get("status"));
          section.setTerm(term);
          section.setStudentCount(Integer.parseInt(record.get(
              "student_count"
          )));
          section.setType(record.get("type"));
          section.setStartDate(dateFormat.parse(record.get("start_date")));
          section.setEndDate(dateFormat.parse(record.get("end_date")));
          section.setDepartment(new Department(record.get("department")));
          section.setProfessor(new Professor(
              Integer.parseInt(record.get("professor_id"))
          ));
          sections.add(section);
        }

        // Close the parser
        parser.close();
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      } catch (ParseException ex) {
        log.error("Parse failure: ", ex);
      }
    }
    return sections;
  }

  private List<Department> readDepartmentFile() {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Department> departments = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      try (CSVParser parser = new CSVParser(new FileReader(file), format)) {

        for (CSVRecord record : parser) {
          Department department = new Department();
          department.setName(record.get("name"));
          department.setAbbreviation(record.get("abbreviation"));

          departments.add(department);
        }

        // Close the parser
        parser.close();
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      }
    }
    return departments;
  }

  private List<MeetingDay> readMeetingDayFile(Term term) {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<MeetingDay> days = new ArrayList<>();
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    // Initialize the CSVParser object
    if (file != null) {
      try (CSVParser parser = new CSVParser(new FileReader(file), format)) {

        for (CSVRecord record : parser) {
          MeetingDay day = new MeetingDay();
          day.setCourseSection(new CourseSection(
              Integer.parseInt(record.get("course_section"))
          ));
          day.setRoom(new Room(record.get("room")));
          day.setDay(record.get("day"));
          day.setStartTime(timeFormat.parse(record.get("start_time")));
          day.setEndTime(timeFormat.parse(record.get("end_time")));
          day.setTerm(term);

          days.add(day);
        }

        // Close the parser
        parser.close();
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      } catch (ParseException ex) {
        log.error("Parse failure: ", ex);
      }
    }
    return days;
  }

  private List<Professor> readProfessorFile() {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Professor> professors = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      try (CSVParser parser = new CSVParser(new FileReader(file), format)) {

        for (CSVRecord record : parser) {
          Professor professor = new Professor();
          professor.setFirstName(record.get("first_name"));
          professor.setLastName(record.get("last_name"));
          professor.setDepartment(new Department(record.get("department")));

          professors.add(professor);
        }

        // Close the parser
        parser.close();
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      }
    }
    return professors;
  }

  private List<Room> readRoomFile() {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Room> rooms = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      try (CSVParser parser = new CSVParser(new FileReader(file), format)) {

        for (CSVRecord record : parser) {
          Room room = new Room();
          room.setRoomNumber(record.get("room_number"));
          room.setBuilding(record.get("building"));
          room.setCapacity(Integer.parseInt(record.get("capacity")));
          room.setType(record.get("type"));

          rooms.add(room);
        }

        // Close the parser
        parser.close();
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      }
    }
    return rooms;
  }

  private List<Term> readTermFile() {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Term> terms = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      try (CSVParser parser = new CSVParser(new FileReader(file), format)) {

        for (CSVRecord record : parser) {
          Term term = new Term();
          term.setTerm(record.get("term"));
          term.setSeason(record.get("season"));
          term.setYear(Integer.parseInt(record.get("year")));
          term.setStatus(record.get("status"));

          terms.add(term);
        }

        // Close the parser
        parser.close();
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      }
    }
    return terms;
  }

  private List<User> readUserFile() {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<User> users = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      try (CSVParser parser = new CSVParser(new FileReader(file), format)) {

        for (CSVRecord record : parser) {
          User user = new User();
          user.setUsername(record.get("username"));
          user.setPassword(record.get("password"));
          user.setFirstname(record.get("firstname"));
          user.setLastname(record.get("lastname"));
          user.setEmail(record.get("email"));
          user.setEnabled(Boolean.parseBoolean(record.get("enabled")));
          user.setDepartment(new Department(record.get("department")));

          users.add(user);
        }

        // Close the parser
        parser.close();
      } catch (IOException ex) {
        log.error("IO failure: ", ex);
      }
    }
    return users;
  }

}
