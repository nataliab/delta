package io.flow.delta.lib

import io.flow.delta.v0.models.Version
import org.scalatestplus.play.PlaySpec

class StateFormatterSpec extends PlaySpec {

  "Nil" in {
    StateFormatter.label(Nil) must be("")
  }

  "single version" in {
    StateFormatter.label(
      Seq(Version("0.0.1", 1))
    ) must be("0.0.1: 1 instance")

    StateFormatter.label(
      Seq(Version("0.0.2", 2))
    ) must be("0.0.2: 2 instances")
  }

  "multiple versions sorted by tag" in {
    StateFormatter.label(
      Seq(
        Version("0.0.2", 1),
        Version("1.2.1", 2),
        Version("0.0.3", 3),
        Version("0.0.1", 4)
      )
    ) must be("0.0.1: 4 instances, 0.0.2: 1 instance, 0.0.3: 3 instances, 1.2.1: 2 instances")
  }

  "strips versions w/ no instances" in {
    StateFormatter.label(
      Seq(
        Version("0.0.2", 0),
        Version("1.2.1", 1)
      )
    ) must be("1.2.1: 1 instance")
  }

  "non semver at end" in {
    StateFormatter.label(
      Seq(
        Version("0.0.1", 1),
        Version("other", 2)
      )
    ) must be("0.0.1: 1 instance, other: 2 instances")
  }

}
