package com.pitong.house.user.service;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pitong.house.user.common.PageParams;
import com.pitong.house.user.mapper.AgencyMapper;
import com.pitong.house.user.model.Agency;
import com.pitong.house.user.model.User;

@Service
public class AgencyService {

	@Autowired
	private AgencyMapper agencyMapper;

	public Pair<List<User>, Long> getAllAgent(PageParams pageParams) {
		List<User> agents = agencyMapper.selectAgent(new User(), pageParams);
		Long count = agencyMapper.selectAgentCount(new User());
		return ImmutablePair.of(agents, count);
	}

	public User getAgentDetail(Long id) {
		User user = new User();
		user.setId(id);
		user.setType(2);
		List<User> list = agencyMapper.selectAgent(user, new PageParams(1, 1));
		if (!list.isEmpty()) {
			User agent = list.get(0);
			Agency agency = new Agency();
			agency.setId(agent.getAgencyId().intValue());
			List<Agency> agencies = agencyMapper.select(agency);
			if (!agencies.isEmpty()) {
				agent.setAgencyName(agencies.get(0).getName());
			}
			return agent;
		}
		return null;
	}

}
