package id.co.map.mapwebreportapplication.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.map.mapwebreportapplication.model.Store;
import id.co.map.mapwebreportapplication.model.User;
import id.co.map.mapwebreportapplication.model.response.ClientUserResponse;
import id.co.map.mapwebreportapplication.service.CommonService;
import id.co.map.mapwebreportapplication.service.UserService;

@RestController
public class CommonController {

	@Autowired
	private CommonService service;
	@Autowired
	private UserService userService;
	
	public CommonController() {
		
	}
	
	@GetMapping("/api/common/store/{location}")
	public List<Store> findStore(@PathVariable("location") String location){
		return service.getStores(location);
	}
	
	@PostMapping("/api/user")
	public ClientUserResponse addUser(@RequestBody User newUser){
		return userService.add(newUser);
	}
	
	@GetMapping("/api/user")
    public Map<String, Object> findSpkPagination(
            @RequestParam(name ="userName", required = false) String userName,
            @RequestParam(name ="name", required = false) String name,
            @RequestParam(name ="roleId", required = false) Integer roleId,
            @RequestParam(name = "draw") Integer draw, //required to avoid paging bugs!
            @RequestParam(name = "pageNumber") Integer pageNumber
    ){
        return userService.findUserPagination(userName, name, roleId, pageNumber, 10, draw);
    }
	
	@DeleteMapping("/api/user/{username}/")
	public ClientUserResponse deleteUser(@PathVariable("username") String username){
		return userService.delete(username);
	}
	
}
