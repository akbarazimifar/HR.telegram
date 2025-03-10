package net.ddns.encante.telegram.hr.quiz.repository;

import net.ddns.encante.telegram.hr.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Quiz findFirstByOrderByKeyIdAsc();
//    returns list of all quiz entities that were either not yet send or has been sent but remain answered badly but not the ones that are waiting in chat to be answered (q.lastAnswer is null for this cases)
    @Query("SELECT q FROM Quiz q WHERE q.dateSent IS NULL OR (q.success = 0 AND q.lastAnswer IS NOT NULL AND q.answersDepleted = 0)")
    List<Quiz> findAllQuizEntitiesToSend();
    List<Quiz> findAllByOrderByKeyIdAsc();
//    returns list of all quiz entities that are waiting in chat to be answered
    @Query("SELECT q FROM Quiz q WHERE q.success = 0 AND q.lastAnswer IS NULL")
    List<Quiz> findAllSentButNotAnsweredQuizEntities();
    @Query("SELECT q FROM Quiz q WHERE q.dateAnswered BETWEEN :currentDate - 604000L AND :currentDate AND q.retriesCount > 0")
    List<Quiz> findAllRetriesFromLastWeek(@Param("currentDate") Long currentDate);
    @Query("SELECT q FROM Quiz q WHERE q.dateAnswered BETWEEN :currentDate - 604000L AND :currentDate")
    List<Quiz>findAllQuizFromLastWeek(@Param("currentDate") Long currentDate);
    @Query("SELECT q FROM Quiz q WHERE q.retriesCount > 0")
    List<Quiz> findAllRetries();
    @Query("SELECT q FROM Quiz q WHERE q.messageId = :messageId AND q.chatId = :chatId")
    Quiz findByCredentials(@Param("messageId") Long messageId, @Param("chatId") Long chatId);
}