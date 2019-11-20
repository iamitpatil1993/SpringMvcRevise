package com.example.mvc.revise.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.mvc.revise.dao.SpittleRepository;
import com.example.mvc.revise.dto.Spittle;

@Controller
@RequestMapping(path = { "/spittles" })
public class SpittleController implements InitializingBean {

	private SpittleRepository spittleRepository;

	@Autowired
	public SpittleController(SpittleRepository spittleRepository) {
		this.spittleRepository = spittleRepository;
	}

	/**
	 * We must set default value for {@link RequestParam}, by default it is required
	 * and spring will throw
	 * 'org.springframework.web.bind.MissingServletRequestParameterException:
	 * Required Integer parameter 'pageSize' is not present' if not set in request
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.GET })
	public List<Spittle> getAllSpittles(@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex) {
		System.out.println("pageSize = " + pageSize + ", pageIndex = " + pageIndex);
		return spittleRepository.findAll().stream().limit(pageSize).collect(Collectors.toList());
	}

	@RequestMapping(path = { "/{spittleId}" }, method = RequestMethod.GET)
	public String getSpittleById(@PathVariable(name = "spittleId") String spittleId, final Model model) {
		if (spittleId.equals("asdf")) {
			throw new SpittleNotFoundException("Spittle not found by id :: " + spittleId);
		}
		final Spittle spittle = spittleRepository.findById(spittleId);
		model.addAttribute(spittle);
		return "spittle";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(spittleRepository, "spittleRepository must be available in application context");
	}

	@GetMapping(path = "/register")
	public String showRegistractionView(Model model) {
		model.addAttribute(new Spittle());
		return "register";
	}

	/**
	 * Post mapping for delete because, Web browser do not support requests other
	 * than GET and POST.
	 * 
	 * @param spittrId
	 * @param model
	 * @return
	 */
	@PostMapping(path = "/delete/{spittrId}")
	public String delete(@PathVariable(name = "spittrId") final String spittrId, Model model) {
		if (spittrId.equals("asdf")) {
			throw new SpittleNotFoundException("Spittr not found by Id :: " + spittrId);
		}
		model.addAttribute("message", "Spittr deleted successfully");
		return "uploadResult";
	}

	@PostMapping(path = "/register")
	public ModelAndView registerSpittlePostHandler(@RequestParam(name = "profilePicture") MultipartFile file,
			@ModelAttribute(name = "spittle") Spittle spittle) {
		ModelAndView modelAndView = new ModelAndView("registrationResult");
		modelAndView.addObject("message", "Spittle registered successfully");
		return modelAndView;
	}

}
