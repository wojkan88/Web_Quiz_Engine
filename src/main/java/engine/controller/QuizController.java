package engine.controller;

import engine.model.*;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/api/quizzes")
    public Page<Quiz> getQuizzes(@RequestParam(name = "page", defaultValue = "0") int page) {
        return quizService.getQuizzes(page);
    }

    @GetMapping("/api/quizzes/completed")
    public Page<CompletionLog> getCompletedQuizzes(@RequestParam(name = "page", defaultValue = "0") int page,
                                                   Authentication authentication) {
        return quizService.getCompletionLogs(page, authentication);
    }

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable long id) {
        return quizService.getQuiz(id);
    }

    @PostMapping("/api/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz, Authentication authentication) {
        return quizService.addQuiz(quiz, authentication);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Response solveQuiz(@PathVariable long id, @RequestBody Answer answer, Authentication authentication) {
        return quizService.solveQuiz(id, answer, authentication);
    }

    @PostMapping("/api/register")
    public void registerUser(@Valid @RequestBody User user) {
        quizService.registerUser(user);
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity deleteQuiz(@PathVariable long id, Authentication authentication) {
        return quizService.deleteQuiz(id, authentication);
    }

    @PutMapping("/api/quizzes/{id}")
    public Quiz updateQuiz(@PathVariable long id, @Valid @RequestBody Quiz quiz, Authentication authentication) {
        return quizService.updateQuiz(id, quiz, authentication);
    }
}