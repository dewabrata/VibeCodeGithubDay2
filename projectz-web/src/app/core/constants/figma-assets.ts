// Figma Assets Constants
// Logo and Images
export const FIGMA_ASSETS = {
  // Company Logo
  LOGO_ELITECH: "http://localhost:3845/assets/9cc60e11c0a5199f8343876c1d255ab35c830288.png",

  // User Avatar
  USER_AVATAR: "http://localhost:3845/assets/7fd61205e0d89517a1fa37f65c8213ac3912ccd8.png",

  // Icons
  ICONS: {
    CIRCLE: "http://localhost:3845/assets/80af4be77ae8a209174ba98205eaa974df029cae.svg",
    LOGOUT: "http://localhost:3845/assets/cbb1daa160fcc1885de1519b4998188a46ce0ebc.svg",

    // Package Icons
    PACKAGE: "http://localhost:3845/assets/476dae71c5506864df11a1c2cf662a3f393306e3.svg",
    PACKAGE_FRAME: "http://localhost:3845/assets/5985d09b164d0a63f3362db5f2291bd03bceeddd.svg",
    PACKAGE_BOX: "http://localhost:3845/assets/0212cc69677be91737cd0b430737eac9c48fb229.svg",
    PACKAGE_CORNER: "http://localhost:3845/assets/db81834537ba32c08b313820afdc84f272c8d11f.svg",
    PACKAGE_SIDE1: "http://localhost:3845/assets/d17c0faa790e1a187c7390e14fcf963c8136b9dd.svg",
    PACKAGE_SIDE2: "http://localhost:3845/assets/00167d88bf5a94169d79ec58db8ece30c8fd2f3b.svg",

    // Package Off Icons
    PACKAGE_OFF: "http://localhost:3845/assets/bcee3463e17296a8eccee443f7b1be2db973b4bb.svg",
    PACKAGE_OFF_PART1: "http://localhost:3845/assets/e2ed03dd3035118ef651b45e0d05ecf03be434a5.svg",
    PACKAGE_OFF_PART2: "http://localhost:3845/assets/b1e6bc0f2fd9bb72a0fe2f4bc33e80c0b2aa0e63.svg",
    PACKAGE_OFF_PART3: "http://localhost:3845/assets/b543ec63562050c473173d5b78e8ccf9b8f28a69.svg",
    PACKAGE_OFF_PART4: "http://localhost:3845/assets/615093d136a8717acd37c14728fc57fcb70e4e76.svg",
    PACKAGE_OFF_SLASH: "http://localhost:3845/assets/c8620d2089fa8c8572327aa3c0815a757d635f15.svg",

    // File Chart Icons
    FILE_CHART: "http://localhost:3845/assets/042e1742413e6db425a02ab0567a2247ed105be0.svg",
    FILE_CHART_BACKGROUND: "http://localhost:3845/assets/8ecd2e0bc184a0de94989aeb75ce2d38de95e3a2.svg",
    FILE_CHART_LINE1: "http://localhost:3845/assets/6fbba690d35a4a9ef3a4e61f21e98e9d7f73e040.svg",
    FILE_CHART_LINE2: "http://localhost:3845/assets/e2b630cf67ba92aa8e5ebe17f6c24c0ab267bc08.svg",

    // Arrow Icons
    ARROW_UP: "http://localhost:3845/assets/1238aa834a194d283fa78c413ffb7446dbfaf021.svg",
  },

  // Charts
  CHARTS: {
    MINI_CHART: "http://localhost:3845/assets/bc2d5fd092b82a1317f8334cd56279c3ec59299f.svg",
    LINE_BACKGROUND: "http://localhost:3845/assets/a28e1b0bd0983bfc9a1fae0c6266ec4f8870bbb7.svg",
    LINE_CHART: "http://localhost:3845/assets/01e630031628f503087df83d06836c82978e0c21.svg",
    BAR_CHART: "http://localhost:3845/assets/cf330b35f5c28f77faa4c0b469aae58cf853a6b8.svg"
  },

  // Dividers
  DIVIDERS: {
    HORIZONTAL: "http://localhost:3845/assets/b674cf6e85257da7c8a3a435b46ff20c5304d01e.svg",
    DOTTED: "http://localhost:3845/assets/58a6e2b8bfb843595908a2aba953a617b289c030.svg",
    SIMPLE: "http://localhost:3845/assets/ded8e5de34c8214fcc4169455e9a94243c3d837b.svg"
  }
};

// Asset Type Definitions
export interface FigmaAsset {
  url: string;
  alt?: string;
}

export interface MetricCardIcon {
  type: 'package' | 'package-off' | 'file-chart';
  color: string;
}
