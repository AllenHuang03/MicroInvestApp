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
public final class GetCurrentUserUseCase_Factory implements Factory<GetCurrentUserUseCase> {
  private final Provider<UserRepository> userRepositoryProvider;

  public GetCurrentUserUseCase_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public GetCurrentUserUseCase get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static GetCurrentUserUseCase_Factory create(
      Provider<UserRepository> userRepositoryProvider) {
    return new GetCurrentUserUseCase_Factory(userRepositoryProvider);
  }

  public static GetCurrentUserUseCase newInstance(UserRepository userRepository) {
    return new GetCurrentUserUseCase(userRepository);
  }
}
