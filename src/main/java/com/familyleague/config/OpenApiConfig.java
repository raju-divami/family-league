package com.familyleague.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Family League API",
                version = "1.0.0",
                description = """
                        Backend API for the **Family & Friends Prediction League Platform**.

                        ## Overview
                        Participants predict match outcomes and final standings for real-world cricket leagues.\
                         Points are awarded automatically after each result is published and leaderboards \
                        are recalculated asynchronously.

                        ## Authentication
                        Most endpoints require a JWT Bearer token obtained from `POST /api/auth/login`.\
                         Pass it as `Authorization: Bearer <token>`.

                        ## Roles
                        | Role  | Description |
                        |-------|-------------|
                        | USER  | Standard participant — can join seasons, submit predictions, view leaderboard |
                        | ADMIN | Full access — creates leagues/seasons/matches, publishes results, broadcasts |

                        ## Prediction Lock Rules
                        - **Season predictions** lock **4 hours** before the first match of the season.
                        - **Match predictions** lock **1 hour** before each match starts.
                        - After lock, predictions are visible to all season members (head-to-head view).

                        ## Scoring
                        Every correctly predicted field (winner / toss / player of the match) earns **1 point**.
                        Ranks are recalculated after each confirmed result.
                        """,
                contact = @Contact(
                        name = "Family League Team",
                        email = "admin@familyleague.com"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local development server")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        description = "JWT token obtained from POST /api/auth/login"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().tags(List.of(
                new Tag().name("Authentication")
                        .description("Register, login and refresh JWT tokens"),
                new Tag().name("Users")
                        .description("View and update user profiles; Admin can list/deactivate users"),
                new Tag().name("Leagues")
                        .description("Admin: create and manage league master records"),
                new Tag().name("Seasons")
                        .description("Admin: create seasons under a league, manage lifecycle (DRAFT → OPEN → ACTIVE → COMPLETED → CLOSED); Users: join a season"),
                new Tag().name("Teams")
                        .description("Admin: create teams and assign them to seasons; Users: view team rosters"),
                new Tag().name("Players")
                        .description("Admin: create players and assign them to season-team rosters"),
                new Tag().name("Matches")
                        .description("Admin: schedule matches; Users: view upcoming and past matches"),
                new Tag().name("Match Results")
                        .description("Admin: publish official match results, triggering async leaderboard recalculation"),
                new Tag().name("Match Predictions")
                        .description("Users: submit or update predictions for winner, toss and player of the match before the lock time"),
                new Tag().name("Season Predictions")
                        .description("Users: submit the full final standings prediction (1–N team ranking) before the season lock"),
                new Tag().name("Leaderboard")
                        .description("View season leaderboard rankings and personal point transaction history"),
                new Tag().name("Notifications")
                        .description("Users: view own notifications; Admin: broadcast messages to selected users"),
                new Tag().name("Audit")
                        .description("Admin: query audit trail — who changed what and when")
        ));
    }
}
