package com.ltp.gradesubmission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GradeController {

  List<Grade> studentGrades = Arrays.asList(
    new Grade("Harry", "Potions", "B"),
    new Grade("Hermione", "Arithmancy", "A"),
    new Grade("Ron", "Charms", "C")
  );

  List<Grade> newStudentGrades = new ArrayList<>();

  @GetMapping("/")
  public String gradeForm(
    Model model,
    @RequestParam(required = false) String id
  ) {
    // model.addAttribute("grade", new Grade());
    int index = getGradeIndex(id);
    model.addAttribute(
      "grade",
      index == Constants.NOT_FOUND ? new Grade() : newStudentGrades.get(index)
    );
    return "form";
  }

  @PostMapping("/handleSubmit")
  public String submitForm(Grade grade) {
    int index = getGradeIndex(grade.getId());
    if (index == -1) {
      newStudentGrades.add(grade);
    } else {
      newStudentGrades.set(index, grade);
    }
    return "redirect:/grades";
  }

  @GetMapping("/grades")
  public String getGrades(Model model) {
    // Grade grade = new Grade("Harry", "Potions", "B");
    model.addAttribute("grades", newStudentGrades);
    return "grades";
  }

  public Integer getGradeIndex(String id) {
    for (int i = 0; i < newStudentGrades.size(); i++) {
      if (newStudentGrades.get(i).getId().equals(id)) {
        return i;
      }
    }
    return Constants.NOT_FOUND;
  }
}
