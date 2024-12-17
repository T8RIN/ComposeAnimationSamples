package com.t8rin.animation.samples.ui.components.model

sealed class Question(
    val question: String,
    val yesPath: Question? = null,
    val noPath: Question? = null,
    val animationType: AnimationType? = null
) {
    object Start : Question(
        question = "Нужно ли анимации повторяться?",
        yesPath = RememberInfiniteTransition,
        noPath = IsLayout
    )

    object RememberInfiniteTransition : Question(
        question = "rememberInfiniteTransition",
        animationType = AnimationType.InfiniteTransition
    )

    object IsLayout : Question(
        question = "Это анимация лэйаута?",
        yesPath = SwitchBetweenComposables,
        noPath = AnimateMultiple
    )

    object SwitchBetweenComposables : Question(
        question = "Переключение между несколькими composable, которые имеют различный контент?",
        yesPath = AnimatedContentAnimation,
        noPath = AppearanceAnimation
    )

    object AnimatedContentAnimation : Question(
        question = "AnimatedContent",
        animationType = AnimationType.AnimatedContent
    )

    object AppearanceAnimation : Question(
        question = "Анимация появления?",
        yesPath = AnimateVisibility,
        noPath = NeedAnimateContentSize
    )

    object NeedAnimateContentSize : Question(
        question = "Необходимо анимировать размер?",
        yesPath = ContentSize,
        noPath = OtherProperties
    )

    object OtherProperties : Question(
        question = "Другие параметры лэйаута? (Смещение, отступ, итд)",
        yesPath = IndependentProperties,
        noPath = IsListItemAnimations
    )

    object IsListItemAnimations : Question(
        question = "Анимация элементов списка?",
        yesPath = AnimateItemPlacement,
        noPath = AnswerNotFound
    )

    object AnimateItemPlacement : Question(
        question = "animateItem()",
        animationType = AnimationType.AnimateItemPlacement
    )

    object AnimateVisibility : Question(
        question = "AnimatedVisibility",
        animationType = AnimationType.AnimatedVisibility
    )

    object ContentSize : Question(
        question = "animateContentSize()",
        animationType = AnimationType.AnimateContentSize
    )

    object AnimateMultiple : Question(
        question = "Нужно ли анимировать несколько параметров?",
        yesPath = IndependentProperties,
        noPath = HavePredefinedValues
    )

    object HavePredefinedValues : Question(
        question = "Имеет ли анимация набор предустановленных параметров?",
        yesPath = AnimateAsState,
        noPath = IsGestureDrivenAnimation
    )

    object AnimateAsState : Question(
        question = "animate*AsState()",
        animationType = AnimationType.AnimateAsState
    )

    object IndependentProperties : Question(
        question = "Параметры независимы?",
        yesPath = AnimateAsState,
        noPath = AnimateTogether
    )

    object AnimateTogether : Question(
        question = "Анимация начинается одновременно?",
        yesPath = UpdateTransition,
        noPath = AnimateSuspend
    )

    object UpdateTransition : Question(
        question = "updateTransition",
        animationType = AnimationType.UpdateTransition
    )

    object AnimateSuspend : Question(
        question = "Animatable + animateTo() (с разными таймингами)",
        animationType = AnimationType.Animatable
    )

    object IsGestureDrivenAnimation : Question(
        question = "Анимация зависит от жестов? Ваша анимация единственный источник правды?",
        yesPath = AnimateToSnap,
        noPath = OneShotAnimation
    )

    object AnimateToSnap : Question(
        question = "Animatable + animateTo() / snapTo()",
        animationType = AnimationType.Animatable
    )

    object OneShotAnimation : Question(
        question = "Одноразовая анимация без контроля состояния?",
        yesPath = AnimationState,
        noPath = AnswerNotFound
    )

    object AnimationState : Question(
        question = "AnimationState или animate()",
        animationType = AnimationType.AnimationState
    )

    object AnswerNotFound : Question(
        question = "Ответ не найден. Напишите запрос: goo.gle/compose-feedback"
    )
}