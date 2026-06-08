package com.SeeTohJJ.Backend.auth.service;

import com.SeeTohJJ.Backend.auth.dto.AuthResponseDTO;
import com.SeeTohJJ.Backend.auth.dto.LoginRequestDTO;
import com.SeeTohJJ.Backend.auth.dto.RegisterRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    String register(RegisterRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);

//    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
//
//    @Autowired
//    private UserDao userRepo;
//
//    @Autowired
//    private UserProfileRepository userProfileRepo;
//
//    @Autowired
//    private PasswordEncoder encoder;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private TopicService topicService;
//
//    @Autowired
//    private TopicRepository topicRepo;
//
//    public String register(RegisterRequestDTO request) {
//        logger.info("Starting Register");
//
//        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
//            throw new RuntimeException("Email already exists");
//        }
//
//        User user = new User();
//
//        user.setEmail(request.getEmail());
//        user.setPassword(encoder.encode(request.getPassword()));
//
//        userRepo.save(user);
//
//        setUserProfile(user, request);
//
//        for (String topicId : request.getTopics()) {
//
//            Topic topic = topicRepo.findById(topicId)
//                    .orElseThrow(() ->
//                            new RuntimeException(
//                                    "Topic not found: " + topicId));
//
//            topicService.setUserTopicInterest(user, topic);
//        }
//
//        return "User registered";
//    }
//
//    public AuthResponseDTO login(String email, String password) {
//        logger.info("Starting Login");
//
//        User user = userRepo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!encoder.matches(password, user.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        String token = jwtService.generateToken(user.getEmail());
//
//        return new AuthResponseDTO(token);
//    }
//
//    public String forgotPassword(String email) {
//        logger.info("Starting Forgot Password");
//
//        User user = userRepo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        String token = UUID.randomUUID().toString();
//
//        user.setResetToken(token);
//
//        userRepo.save(user);
//
//        return token;
//    }
//
//    public String resetPassword(String token, String newPassword) {
//        logger.info("Starting Reset Password");
//
//        User user = userRepo.findByResetToken(token)
//                .orElseThrow(() -> new RuntimeException("Invalid token"));
//
//        user.setPassword(encoder.encode(newPassword));
//        user.setResetToken(null);
//
//        userRepo.save(user);
//
//        return "Password reset successful";
//    }
//
//    public void setUserProfile(User user, RegisterRequestDTO request) {
//        logger.info("Starting Set User Profile");
//
//        UserProfile userProfile = new UserProfile();
//
//        userProfile.setUser(user);
//
//        userProfile.setUsername(request.getUsername());
//        userProfile.setGender(request.getGender());
//        userProfile.setAge(request.getAge());
//        userProfile.setEmploymentStatus(request.getEmploymentStatus());
//        userProfile.setIncome(request.getIncome());
//        userProfile.setCountry(request.getCountry());
//
//        userProfileRepo.save(userProfile);
//
//    }

}
