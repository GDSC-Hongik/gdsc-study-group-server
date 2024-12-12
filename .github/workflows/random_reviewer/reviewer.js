const core = require("@actions/core");
const github = require("@actions/github");

const member= {
    cmj7271: "740776211231277066",
    ybkang1108: "1038826581558296636",
    yeeunli: "1082157260249251870",
    JungJaehoon0430: "1007395745223151726"
}

function selectRandomReviewer() {
    const candidate = Object.keys(member).filter(
        (name) => name !== github.context.actor
    )
    return candidate[
        Math.floor(Math.random() * candidate.length)
        ]
}

async function sendDiscordMsg(reviewer) {
    const webhook = process.env.DISCORD_WEBHOOK;

    const msg = {
        content: createMsg(reviewer)
    }

    await fetch(webhook, {
        method: "POST",
        headers: { 'Content-type': 'application/json' },
        body: JSON.stringify(msg)
    })
}

function createMsg(reviewer) {
    return "리뷰해주세요\n" + "* PR: " + `https://github.com/${github.context.repo.owner}/${github.context.repo.repo}/pulls/${github.context.payload.pull_request.number}`
        + "\n* 담당자: @" + "<@&" + member[reviewer] + ">"
}

async function main() {
    const githubClient = github.getOctokit(process.env.REVIEW_TOKEN);
    const reviewer = selectRandomReviewer();

    githubClient.rest.pulls.requestReviewers(
        {
            owner: github.context.repo.owner,
            repo: github.context.repo.repo,
            pull_number: github.context.payload.pull_request.number,
            reviewers: [reviewer]
        }
    )
        .then((res) => console.log("reviewer assign success: ", res))
        .catch((err) => console.log("reviewer assign failed:", err));

    sendDiscordMsg(reviewer)
        .then(() => console.log("message send success"))
        .catch(() => console.log("message send failed"));
}

main().then(() => console.log("success")).catch(() => console.log("failed"));