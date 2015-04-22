package com.coursepresso.project.helper;

import com.coursepresso.project.entity.*;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Caleb Miller
 */
public class ImportFileHelper {

  private static final Logger log = LoggerFactory.getLogger(
      ImportFileHelper.class
  );

  public static List<Appliance> readApplianceFile(File file) throws Exception {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Appliance> appliances = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      CSVParser parser = new CSVParser(new FileReader(file), format);

      for (CSVRecord record : parser) {
        Appliance appliance = new Appliance();
        appliance.setType(record.get("type"));
        appliance.setRoom(new Room(record.get("room_number")));

        appliances.add(appliance);
      }

      // Close the parser
      parser.close();
    }
    return appliances;
  }

  public static List<Authority> readAuthorityFile(File file) throws Exception {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Authority> authorities = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      CSVParser parser = new CSVParser(new FileReader(file), format);

      for (CSVRecord record : parser) {
        Authority authority = new Authority();
        authority.setUser(new User(record.get("username")));
        authority.setAuthority(record.get("authority"));

        authorities.add(authority);
      }

      // Close the parser
      parser.close();
    }
    return authorities;
  }

  public static List<Course> readCourseFile(File file) throws Exception {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Course> courses = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      CSVParser parser = new CSVParser(new FileReader(file), format);

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
    }
    return courses;
  }

  public static List<CoursePrerequisite> readCoursePrerequisiteFile(File file)
      throws Exception {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<CoursePrerequisite> prerequisites = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      CSVParser parser = new CSVParser(new FileReader(file), format);

      for (CSVRecord record : parser) {
        CoursePrerequisite prerequisite = new CoursePrerequisite();
        prerequisite.setCourse(new Course(record.get("course_number")));
        prerequisite.setPrerequisite(new Course(record.get("prerequisite")));

        prerequisites.add(prerequisite);
      }

      // Close the parser
      parser.close();
    }
    return prerequisites;
  }

  public static List<CourseSection> readCourseSectionFile(File file, Term term)
      throws Exception {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<CourseSection> sections = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Initialize the CSVParser object
    if (file != null) {
      CSVParser parser = new CSVParser(new FileReader(file), format);

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
    }
    return sections;
  }

  public static List<Department> readDepartmentFile(File file) throws Exception {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Department> departments = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      CSVParser parser = new CSVParser(new FileReader(file), format);

      for (CSVRecord record : parser) {
        Department department = new Department();
        department.setName(record.get("name"));
        department.setAbbreviation(record.get("abbreviation"));

        departments.add(department);
      }

      // Close the parser
      parser.close();
    }
    return departments;
  }

  public static List<MeetingDay> readMeetingDayFile(File file, Term term) throws
      Exception {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<MeetingDay> days = new ArrayList<>();
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    // Initialize the CSVParser object
    if (file != null) {
      CSVParser parser = new CSVParser(new FileReader(file), format);

      for (CSVRecord record : parser) {
        MeetingDay day = new MeetingDay();
        day.setCourseSection(new CourseSection(
            Integer.parseInt(record.get("course_section_id"))
        ));
        day.setRoom(new Room(record.get("room_number")));
        day.setDay(record.get("day"));
        day.setStartTime(timeFormat.parse(record.get("start_time")));
        day.setEndTime(timeFormat.parse(record.get("end_time")));
        day.setTerm(term);

        days.add(day);
      }

      // Close the parser
      parser.close();
    }
    return days;
  }

  public static List<Professor> readProfessorFile(File file) throws Exception {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Professor> professors = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      CSVParser parser = new CSVParser(new FileReader(file), format);

      for (CSVRecord record : parser) {
        Professor professor = new Professor();
        professor.setFirstName(record.get("first_name"));
        professor.setLastName(record.get("last_name"));
        professor.setDepartment(new Department(record.get("department")));

        professors.add(professor);
      }

      // Close the parser
      parser.close();
    }
    return professors;
  }

  public static List<Room> readRoomFile(File file) throws Exception {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Room> rooms = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      CSVParser parser = new CSVParser(new FileReader(file), format);

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
    }
    return rooms;
  }

  public static List<Term> readTermFile(File file) throws Exception {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<Term> terms = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      CSVParser parser = new CSVParser(new FileReader(file), format);

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
    }
    return terms;
  }

  public static List<User> readUserFile(File file) throws Exception {
    // Create the CSVFormat object
    CSVFormat format = CSVFormat.DEFAULT.withHeader().withDelimiter(';');
    List<User> users = new ArrayList<>();

    // Initialize the CSVParser object
    if (file != null) {
      CSVParser parser = new CSVParser(new FileReader(file), format);

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
    }
    return users;
  }
}
