package com.example.muahexanh_resigtration.repositories;

import com.example.muahexanh_resigtration.entities.ProjectEntity;
import com.example.muahexanh_resigtration.entities.StudentEntity;
import com.example.muahexanh_resigtration.entities.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    @Query("SELECT p FROM ProjectEntity p WHERE p.id = :projectId")
    Optional<ProjectEntity> getDetailProject(@Param("projectId") Long projectId);

//    @Query("SELECT p FROM ProjectEntity p JOIN p.students s WHERE s.id = :leaderId")
//    Optional<List<ProjectEntity>> getAllProjectByLeaderId(@Param("leaderId") Long leaderId);

    @Query("SELECT p FROM CommunityLeaderEntity c JOIN c.projects p WHERE c.id = :communityLeaderId AND p.id = :projectId")
    Optional<ProjectEntity> getProjectByLeaderIdAndProjectId(@Param("communityLeaderId") Long communityLeaderId, @Param("projectId") Long projectId);

//    @Query("SELECT s FROM ProjectEntity s JOIN s.students st WHERE st.id = :studentId")
//    Optional<List<ProjectEntity>> findByStudentId(@Param("studentId") Long studentId);
//

    @Query("SELECT p.maxSchoolRegistrationMembers FROM ProjectEntity p WHERE p.id = :projectId")
    Optional<Integer> getmaxSchoolRegistrationMembers(@Param("projectId") Long projectId);
}
