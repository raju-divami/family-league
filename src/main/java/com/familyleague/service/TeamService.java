package com.familyleague.service;

import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.request.AddTeamToSeasonRequest;
import com.familyleague.dto.request.CreateTeamRequest;
import com.familyleague.dto.request.UpdateTeamRequest;
import com.familyleague.dto.response.SeasonTeamResponse;
import com.familyleague.dto.response.TeamResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamService {

    TeamResponse createTeam(CreateTeamRequest request);

    TeamResponse getTeamById(Long id);

    PagedResponse<TeamResponse> getAllTeams(Pageable pageable);

    TeamResponse updateTeam(Long id, UpdateTeamRequest request);

    void deleteTeam(Long id);

    SeasonTeamResponse addTeamToSeason(Long seasonId, AddTeamToSeasonRequest request);

    List<SeasonTeamResponse> getTeamsBySeason(Long seasonId);
}
