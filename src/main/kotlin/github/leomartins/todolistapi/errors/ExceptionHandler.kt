package github.leomartins.todolistapi.errors

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.IllegalStateException

@ControllerAdvice
class ExceptionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(
        value = HttpStatus.NOT_FOUND,
        reason = RESOURCE_NOT_FOUND_ERROR
    )
    fun noSuchElement(ex: NoSuchElementException) {
        logger.error("Exception thrown:", ex)
    }

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(
        value = HttpStatus.FORBIDDEN,
        reason = ILLEGAL_OPERATION_ERROR
    )
    fun illegalStateException(ex: IllegalStateException) {
        logger.error("Exception thrown:", ex)
    }

}