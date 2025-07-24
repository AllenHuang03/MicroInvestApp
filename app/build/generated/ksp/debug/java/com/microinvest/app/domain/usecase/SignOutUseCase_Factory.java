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
public final class SignOutUseCase_Factory implements Factory<SignOutUseCase> {
  private final Provider<UserRepository> userRepositoryProvider;

  public SignOutUseCase_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public SignOutUseCase get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static SignOutUseCase_Factory create(Provider<UserRepository> userRepositoryProvider) {
    return new SignOutUseCase_Factory(userRepositoryProvider);
  }

  public static SignOutUseCase newInstance(UserRepository userRepository) {
    return new SignOutUseCase(userRepository);
  }
}
