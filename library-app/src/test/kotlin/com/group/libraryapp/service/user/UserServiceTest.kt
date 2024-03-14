package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {
    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }

    @Test
    fun saveUserTest() {
        val request = UserCreateRequest("김승래", 20)

        userService.saveUser(request)

        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo(request.name)
        assertThat(results[0].age).isEqualTo(request.age)
    }

    @Test
    fun getUsersTest() {
        userRepository.saveAll(
            listOf(
                User("A", 20),
                User("B", 30),
            ),
        )

        val results = userService.getUsers()

        assertThat(results).hasSize(2)
        assertThat(results).extracting("name")
            .containsExactlyInAnyOrder("A", "B")
        assertThat(results).extracting("age")
            .containsExactlyInAnyOrder(20, 30)
    }

    @Test
    fun updateUserNameTest() {
        val savedUser = userRepository.save(User("A", 20))
        val request = UserUpdateRequest(savedUser.id!!, "B")

        userService.updateUserName(request)

        val result = userRepository.findAll()[0]

        assertThat(result.name).isEqualTo("B")
    }

    @Test
    fun deleteUserTest() {
        userRepository.save(User("A", 20))

        userService.deleteUser("A")

        assertThat(userRepository.findAll()).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 없는 유저도 응답에 포함된다.")
    fun getUserLoanHistoriesTest1() {
        userRepository.save(User("A", null))

        val results = userService.getUserLoanHistories()

        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 많은 유저의 응답이 정상 동작한다.")
    fun getUserLoanHistoriesTest2() {
        val savedUser = userRepository.save(User("A", null))
        userLoanHistoryRepository.saveAll(
            listOf(
                UserLoanHistory.fixture(savedUser, "책1", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(savedUser, "책2", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(savedUser, "책3", UserLoanStatus.RETURNED)
            )
        )

        val results = userService.getUserLoanHistories()

        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).hasSize(3)
        assertThat(results[0].books).extracting("name")
            .containsExactlyInAnyOrder("책1", "책2", "책3")
        assertThat(results[0].books).extracting("isReturn")
            .containsExactlyInAnyOrder(false, false, true)

    }

    @Test
    @DisplayName("방금 두 경우가 합쳐진 테스트")
    fun getUserLoanHistoriesTest3() {
        val savedUsers = userRepository.saveAll(
            listOf(
                User("A", null),
                User("B", null)
            ))
        userLoanHistoryRepository.saveAll(
            listOf(
                UserLoanHistory.fixture(savedUsers[0], "책1", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(savedUsers[0], "책2", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(savedUsers[0], "책3", UserLoanStatus.RETURNED)
            )
        )

        val results = userService.getUserLoanHistories()

        assertThat(results).hasSize(2)
        val userAResult = results.first() { it.name == "A"}
        assertThat(userAResult.books).hasSize(3)
        assertThat(userAResult.books).extracting("name")
            .containsExactlyInAnyOrder("책1", "책2", "책3")
        assertThat(userAResult.books).extracting("isReturn")
            .containsExactlyInAnyOrder(false, false, true)

        val userBResult = results.first() { it.name == "B"}
        assertThat(userBResult.books).isEmpty()
    }
}
