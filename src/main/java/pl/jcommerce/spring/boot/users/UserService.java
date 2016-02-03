package pl.jcommerce.spring.boot.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.HystrixCollapser.Scope;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

	private final UserRepository userRepository;
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	@HystrixCollapser(batchMethod = "getUserList", scope = Scope.GLOBAL)
	public User getUser(@PathVariable("id") Long id) {
		return null;
	}

	@HystrixCommand
	public List<User> getUserList(List<Long> ids) {
		return ids.stream().map(id -> userRepository.findOne(id)).collect(Collectors.toList());
	}
}
