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
public final class SignUpUseCase_Factory implements Factory<SignUpUseCase> {
  private final Provider<UserRepository> userRepositoryProvider;

  public SignUpUseCase_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public SignUpUseCase get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static SignUpUseCase_Factory create(Provider<UserRepository> userRepositoryProvider) {
    return new SignUpUseCase_Factory(userRepositoryProvider);
  }

  public static SignUpUseCase newInstance(UserRepository userRepository) {
    return new SignUpUseCase(userRepository);
  }
}
