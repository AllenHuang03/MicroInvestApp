package com.microinvest.app.domain.usecase;

import com.microinvest.app.domain.repository.UserRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class SignInUseCase_Factory implements Factory<SignInUseCase> {
  private final Provider<UserRepository> userRepositoryProvider;

  public SignInUseCase_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public SignInUseCase get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static SignInUseCase_Factory create(Provider<UserRepository> userRepositoryProvider) {
    return new SignInUseCase_Factory(userRepositoryProvider);
  }

  public static SignInUseCase newInstance(UserRepository userRepository) {
    return new SignInUseCase(userRepository);
  }
}
