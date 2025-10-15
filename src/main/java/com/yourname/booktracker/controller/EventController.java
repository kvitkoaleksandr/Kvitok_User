package com.yourname.booktracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Event API", description = "API for managing events")
public class EventController {
    private final EventService eventService;
    private final EventValidator eventValidator;

    @PostMapping // Создать событие
    @Operation(summary = "Create an event", description = "Creates a new event")
    public EventDto create(@RequestBody EventDto eventDto) {
        if (!eventValidator.validateEventDto(eventDto)) {
            throw new DataValidationException("Не валидные данные в EventDto");
        }
        return eventService.create(eventDto);
    }

    @GetMapping("/{eventId}") //Получить событие
    @Operation(summary = "Get an event", description = "Retrieves an event by its ID")
    public EventDto getEvent(@PathVariable @Parameter(description = "ID of the event to retrieve") Long eventId) {
        if (!eventValidator.validateId(eventId)) {
            throw new DataValidationException("Не валидный id");
        }
        return eventService.getEvent(eventId);
    }

    @PostMapping("/filter") // Получить все события с фильтрами
    @Operation(summary = "Get events by filter", description = "Retrieves events based on the specified filter")
    public List<EventDto> getEventsByFilter(@RequestBody EventFilterDto filter) {
        return eventService.getEventsByFilter(filter);
    }

    @DeleteMapping("/{eventId}") // Удалить событие
    @Operation(summary = "Delete an event", description = "Deletes an event by its ID")
    public void deleteEvent(@PathVariable @Parameter(description = "ID of the event to delete") Long eventId) {
        if (!eventValidator.validateId(eventId)) {
            throw new DataValidationException("Не валидный id");
        }
        eventService.deleteEvent(eventId);
    }

    @PutMapping // Обновить событие
    @Operation(summary = "Update an event", description = "Updates an existing event")
    public EventDto updateEvent(@RequestBody EventDto eventDto) {
        if (!eventValidator.validateEventDto(eventDto)) {
            throw new DataValidationException("Не валидные данные в EventDto");
        }
        return eventService.updateEvent(eventDto);
    }

    @GetMapping("/owned/{userId}") // Получить все созданные пользователем события
    @Operation(summary = "Get owned events", description = "Retrieves events owned by the specified user")
    public List<EventDto> getOwnedEvents(@PathVariable @Parameter(
            description = "ID of the user whose events are to be retrieved") long userId) {
        if (!eventValidator.validateId(userId)) {
            throw new DataValidationException("Не валидный id");
        }
        return eventService.getOwnedEvents(userId);
    }

    @GetMapping("/participated/{userId}") // Получить все события, в которых пользователь принимает участие
    @Operation(summary = "Get participated events", description = "Retrieves events participated by the specified user")
    public List<EventDto> getParticipatedEvents(@PathVariable @Parameter(
            description = "ID of the user whose participated events are to be retrieved") long userId) {
        if (!eventValidator.validateId(userId)) {
            throw new DataValidationException("Не валидный id");
        }
        return eventService.getParticipatedEvents(userId);
    }
}