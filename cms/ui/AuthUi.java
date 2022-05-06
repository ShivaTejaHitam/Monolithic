package com.epam.cms.ui;

import java.util.Optional;

import com.epam.cms.dto.InstructorDto;

public interface AuthUi {
	Optional<InstructorDto> authenticate();
}
