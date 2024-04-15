package jp.gihyo.projava.tasklist;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {

    record TaskItem(String id, String task, String deadline, Integer done) {}
    private List<TaskItem> taskItems = new ArrayList<>();
    private final TaskListDao dao;

//    public String getStatus() {
//        switch () {
//            case 0:
//                return "未完了";
//            case 1:
//                return "対応中";
//            case 2:
//                return "完了";
//        }
//    }

    @Autowired
    HomeController(TaskListDao dao) {
        this.dao = dao;
    }

    @RequestMapping("/hello")
    @ResponseBody
    String hello(Model model) {
        model.addAttribute("time", LocalDateTime.now());
        return "hello";
    }

    @GetMapping("/list")
    String listItems(Model model) {
        List<TaskItem> taskItems = dao.findAll();
        model.addAttribute("taskList", taskItems);
        return "home";
    }

    @GetMapping("/add")
    String addItem(@RequestParam("task") String task,
                   @RequestParam("deadline") String deadline,
                   @RequestParam(defaultValue = "0", name ="done") Integer done) { //defaultValue = "0", name =
        String id = UUID.randomUUID().toString().substring(0, 8);
        TaskItem item = new TaskItem(id, task, deadline, done);
        dao.add(item);

        return "redirect:/list";
    }

    @GetMapping("/delete")
    String deleteItem(@RequestParam("id") String id) {
        dao.delete(id);
        return "redirect:/list";
    }

    @GetMapping("/update")
    String updateItem(@RequestParam("id") String id,
                      @RequestParam("task") String task,
                      @RequestParam("deadline") String deadline,
                      @RequestParam("done") Integer done) {
        TaskItem taskItem = new TaskItem(id, task, deadline, done);
        dao.update(taskItem);
        return "redirect:/list";
    }



}
