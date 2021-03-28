package engine.service;

import engine.model.*;
import engine.repository.CompletionLogRepository;
import engine.repository.QuizRepository;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private CompletionLogRepository completionLogRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<Quiz> getQuizzes(int page) {
        return quizRepository.findAll(PageRequest.of(page, 10));
    }

    public Page<CompletionLog> getCompletionLogs(int page, Authentication authentication) {
        return completionLogRepository.findAllByUsername(authentication.getName(),
                PageRequest.of(page, 10, Sort.by("completedAt").descending()));
    }

    public Quiz getQuiz(long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such quiz"));
    }

    public Quiz addQuiz(Quiz quiz, Authentication authentication) {
        quiz.setCreator(authentication.getName());
        return quizRepository.save(quiz);
    }

    public Response solveQuiz(long id, Answer answer, Authentication authentication) {
        if (Arrays.equals(answer.getAnswer(), quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such quiz")).getAnswer())) {
            completionLogRepository.save(new CompletionLog(id, LocalDateTime.now(), authentication.getName()));
            return new Response(true, "Congratulations, you're right!");
        } else {
            return new Response(false, "Wrong answer! Please, try again.");
        }
    }

    public void registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with given email already exists");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    public ResponseEntity deleteQuiz(long id, Authentication authentication) {
        if (quizRepository.findById(id).isPresent()) {
            if (quizRepository.findById(id).get().getCreator().equals(authentication.getName())) {
                quizRepository.deleteById(id);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can not delete other user's quiz");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz with given id does not exist");
        }
    }

    public Quiz updateQuiz(long id, Quiz quiz, Authentication authentication) {
        if (quizRepository.findById(id).isPresent()) {
            if (quizRepository.findById(id).get().getCreator().equals(authentication.getName())) {
                quiz.setId(id);
                return quizRepository.save(quiz);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can not update other user's quiz");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz with given id does not exist");
        }
    }
}